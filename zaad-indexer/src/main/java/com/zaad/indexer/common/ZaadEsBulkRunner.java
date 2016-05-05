package com.zaad.indexer.common;

import com.carrotsearch.hppc.cursors.ObjectCursor;
import com.zaad.indexer.common.util.ZaadEsNamingManager;
import com.zaad.indexer.common.util.ZaadEsSchemFileManager;
import com.zaad.indexer.runner.TutorEsBulkRunner;
import com.zaad.indexer.transport.ZaadEsClient;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesResponse;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.SettingsException;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * 벌크색인을 위한 추상클래스.
 * Alias를 이용하여 서비스중인 Index에 영향 없이 다른 index에 색인 완료 후 Alias를 교체하기위
 * 새로운 index를 초기화 하는 역할을 한다.
 *
 * @author socurites, lks21c
 */
public abstract class ZaadEsBulkRunner {
    /**
     * 로거
     */
    private static Logger logger = LoggerFactory.getLogger(TutorEsBulkRunner.class);

    protected String indexName;
    protected String typeName;
    protected String defaultIndexName;
    protected String aliasName;
    protected String newIndexName;

    protected ZaadEsClient client;

    /**
     * 하나의 요청에 색인되는 벌크 단위
     */
    protected static final int BULK_SIZE = 1000;

    // 벌크 색인 리스너 설정
    protected BulkProcessor.Listener listener = new BulkProcessor.Listener() {
        @Override
        public void beforeBulk(long executionId, BulkRequest request) {

        }

        // 성공시
        @Override
        public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
            System.out.println("response.getItems()[0].getId() = " + response.getItems()[0].getId());
        }

        // 실패시
        @Override
        public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
            System.out.println("fail");
            System.out.println(failure.getMessage());
        }
    };

    // 벌크 프로세서 설정
    protected BulkProcessor processor;

    protected void init() {
        this.defaultIndexName = ZaadEsNamingManager.getDefaultIndexName(this.getIndexName());
        this.aliasName = ZaadEsNamingManager.getIndexAliasName(this.getIndexName());
        this.newIndexName = createIndex(this.indexName, this.typeName, this.defaultIndexName, this.aliasName);
        processor = BulkProcessor.builder(this.client.getClient(), listener)
                .setBackoffPolicy(BackoffPolicy.noBackoff())
                .setConcurrentRequests(1)
                .setBulkActions(BULK_SIZE)
                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
                .build();
    }

    protected abstract void bulk() throws FileNotFoundException, InterruptedException;

    protected String createIndex(String indexName, String typeName, String defaultIndexName, String aliasName) {
        String nextIndexName = getNextIndexName(aliasName, defaultIndexName);

        if (client.getClient().admin().indices().exists(
                new IndicesExistsRequest(nextIndexName)).actionGet().isExists()) {
            CloseIndexRequest closeRequest = new CloseIndexRequest(nextIndexName);
            client.getClient().admin().indices().close(closeRequest).actionGet();
            DeleteIndexRequest deleteRequest = new DeleteIndexRequest(nextIndexName);
            client.getClient().admin().indices().delete(deleteRequest).actionGet();
        }

        Settings settings = null;
        try {
            System.out.println(ZaadEsSchemFileManager.getSettingsPath(indexName, typeName));
            settings = Settings.settingsBuilder()
                    .loadFromSource(ZaadEsSchemFileManager.readSettings(indexName, typeName))
                    .build();
        } catch (SettingsException e) {
            e.printStackTrace();
        }

        CreateIndexRequest request = new CreateIndexRequest(nextIndexName, settings);
        client.getClient().admin().indices().create(request).actionGet();

        client.getClient().admin().indices()
                .preparePutMapping(nextIndexName)
                .setType(typeName)
                .setSource(ZaadEsSchemFileManager.readTypeMapping(indexName, typeName))
                .execute()
                .actionGet()
        ;

        return nextIndexName;
    }

    protected void beforeExit(String nextIndexName, String defaultIndexName, String aliasName) {
        changeAlias(nextIndexName, defaultIndexName, aliasName);
        RefreshRequest refreshRequest = new RefreshRequest(aliasName);
        client.getClient().admin().indices().refresh(refreshRequest).actionGet();
    }

    private void changeAlias(String nextIndexName, String defaultIndexName, String aliasName) {
        String currentIndexName = getIndexNameByAlias(aliasName);

        if (currentIndexName != null && !currentIndexName.isEmpty()) {
            client.getClient().admin().indices().prepareAliases()
                    .removeAlias(currentIndexName, aliasName)
                    .addAlias(nextIndexName, aliasName)
                    .execute()
                    .actionGet()
            ;
        } else {
            client.getClient().admin().indices().prepareAliases()
                    .addAlias(defaultIndexName, aliasName)
                    .execute()
                    .actionGet()
            ;
        }
    }

    /**
     * 벌크색인을 위해 2개의 index를 번갈아 활용하기 위해 다음 벌크색인용 index명을 구한다.
     * currentIndexName 뒤에 "_1", "_2"를 이용하여 서비스중인 index가 아닌 index로 색인을 수행 하고자 한다.
     * 예를들어, 현재index가 "index_1"이면 벌크 색인을 위한 index명은 "index_2"이다.
     * 현재인덱스가 "index_2"이면 벌크색인을 위한 index명은 "index_1"이된다.
     *
     * @param aliasName
     * @param defaultIndexName
     * @return
     */
    private String getNextIndexName(String aliasName, String defaultIndexName) {
        String currentIndexName = getIndexNameByAlias(aliasName);

        if (currentIndexName != null && !currentIndexName.isEmpty()) {
            String[] tokens = currentIndexName.split("_");

            int currentSeq = Integer.parseInt(tokens[1]);
            if (currentSeq == 1) {
                tokens[1] = new String("2");
            } else {
                tokens[1] = new String("1");
            }

            return StringUtils.join(tokens, "_");
        } else {
            return defaultIndexName;
        }
    }

    /**
     * aliasName이 가진 실제 색인명을 획득한다.
     *
     * @param aliasName
     * @return
     */
    private String getIndexNameByAlias(String aliasName) {
        GetAliasesResponse getAliasesResponse = client.getClient().admin().indices()
                .getAliases(new GetAliasesRequest(aliasName)).actionGet();
        ImmutableOpenMap<String, List<AliasMetaData>> aliasMap = getAliasesResponse.getAliases();

        for (ObjectCursor<String> key : aliasMap.keys()) {
            return key.value;
        }
        return null;
    }

    /**
     * @return the indexName
     */
    public String getIndexName() {
        return indexName;
    }

    /**
     * @param indexName the indexName to set
     */
    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    /**
     * @return the typeName
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * @param typeName the typeName to set
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * @return the defaultIndexName
     */
    public String getDefaultIndexName() {
        return defaultIndexName;
    }

    /**
     * @param defaultIndexName the defaultIndexName to set
     */
    public void setDefaultIndexName(String defaultIndexName) {
        this.defaultIndexName = defaultIndexName;
    }

    /**
     * @return the aliasName
     */
    public String getAliasName() {
        return aliasName;
    }

    /**
     * @param aliasName the aliasName to set
     */
    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    /**
     * @return the newIndexName
     */
    public String getNewIndexName() {
        return newIndexName;
    }

    /**
     * @param newIndexName the newIndexName to set
     */
    public void setNewIndexName(String newIndexName) {
        this.newIndexName = newIndexName;
    }
}
