package com.zaad.indexer.common;

import com.zaad.indexer.common.util.ZaadEsNamingManager;
import com.zaad.indexer.common.util.ZaadEsSchemFileManager;
import com.zaad.indexer.transport.ZaadEsClient;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesResponse;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.SettingsException;

import java.util.List;

public abstract class ZaadEsBulkRunner {
    protected String indexName;
    protected String typeName;
    protected String defaultIndexName;
    protected String aliasName;
    protected String newIndexName;

    protected ZaadEsClient client;

    protected void init() {
        this.defaultIndexName = ZaadEsNamingManager.getDefaultIndexName(this.getIndexName());
        this.aliasName = ZaadEsNamingManager.getIndexAliasName(this.getIndexName());

        this.newIndexName = createIndex(this.indexName, this.typeName, this.defaultIndexName, this.aliasName);
    }

    protected abstract void bulk();

    protected String createIndex(String indexName, String typeName, String defaultIndexName, String aliasName) {
        String nextIndexName = getNextIndexName(aliasName, defaultIndexName);

        if (client.getClient().admin().indices().exists(new IndicesExistsRequest(nextIndexName)).actionGet().isExists()) {
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

    private String getIndexNameByAlias(String aliasName) {
        GetAliasesResponse getAliasesResponse = client.getClient().admin().indices().getAliases(new GetAliasesRequest(aliasName)).actionGet();
        ImmutableOpenMap<String, List<AliasMetaData>> aliasMap = getAliasesResponse.getAliases();

        if (aliasMap != null && !aliasMap.isEmpty()) {
            return aliasMap.keys().iterator().next().value;
        } else {
            return null;
        }
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
