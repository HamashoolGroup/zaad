package com.zaad.indexer.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaad.common.domain.Tutor;
import com.zaad.common.exception.ZaadDomainCreationException;
import com.zaad.common.util.ZaadOutputDirectoryManager;
import com.zaad.indexer.common.ZaadEsBulkRunner;
import com.zaad.indexer.sitemap.TutorSiteMapGenerator;
import com.zaad.indexer.transport.ZaadEsClient;
import com.zaad.indexer.transport.ZaadEsTransportClient;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author socurites, lks21c
 */
public class TutorEsBulkRunner extends ZaadEsBulkRunner {
    /**
     * 하나의 요청에 색인되는 벌크 단위
     */
    protected static final int BULK_SIZE = 1000;

    /**
     * 색인이름
     */
    private static final String INDEX_NAME = "tutor";

    /**
     * 타입이름
     */
    private static final String TYPE_NAME = "detail";

    /**
     * 로거
     */
    private static Logger logger = LoggerFactory.getLogger(TutorEsBulkRunner.class);

    public static void main(String[] args) {
        ZaadEsClient zaadEsClient = new ZaadEsTransportClient();
        TutorEsBulkRunner runner = new TutorEsBulkRunner(zaadEsClient);
        runner.bulk();
    }

    public TutorEsBulkRunner(ZaadEsClient zaadEsClient) {
        this.client = zaadEsClient;
    }

    @Override
    protected void init() {
        this.indexName = INDEX_NAME;
        this.typeName = TYPE_NAME;
        super.init();
    }

    @Override
    protected void bulk() {
        init();
        BulkRequestBuilder bulRequestBuilder = this.client.getClient().prepareBulk();
        List<String> tutorIds = ZaadOutputDirectoryManager.getDataTutorIds();
        ObjectMapper mapper = new ObjectMapper();
        TutorSiteMapGenerator siteMapGenerator = new TutorSiteMapGenerator();
        for (String tutorId : tutorIds) {
            try {
                Tutor tutor = readTutor(tutorId);

                if (tutor != null) {
                    bulRequestBuilder.add(client.getClient().prepareIndex(newIndexName, typeName, tutor.getTutorId())
                            .setSource(mapper.writeValueAsString(tutor))
                    );

                    siteMapGenerator.appendUrl(tutor);
                }

                if (bulRequestBuilder.numberOfActions() > BULK_SIZE) {
                    BulkResponse bulkResponse = bulRequestBuilder.execute().actionGet();

                    if (bulkResponse.hasFailures()) {
                        // TODO: do something
                    }

                    bulRequestBuilder = this.client.getClient().prepareBulk();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if (bulRequestBuilder.numberOfActions() > 0) {
            BulkResponse bulkResponse = bulRequestBuilder.execute().actionGet();

            if (bulkResponse.hasFailures()) {
                // TODO: do something
            }
        }

        siteMapGenerator.close();

        beforeExit(newIndexName, newIndexName, aliasName);
    }

    public static Tutor readTutor(String tutorId) throws Exception {
        File tutorDataFile = ZaadOutputDirectoryManager.getTutorDataFile(tutorId);
        if (!tutorDataFile.exists()) {
            throw new FileNotFoundException("tutorDataFile not found");
        }

        Scanner scanner = null;
        String line;
        Tutor tutor = null;
        try {
            scanner = new Scanner(tutorDataFile);

            List<String> lines = new ArrayList<String>();
            while (scanner.hasNext()) {
                line = scanner.nextLine();
                if (line != null) {
                    lines.add(line);
                }
            }

            try {
                tutor = new Tutor(lines);
                tutor.setTutorId(tutorId);
            } catch (ZaadDomainCreationException e) {
                logger.error(e.getMessage() + " for: " + tutorId);
            }
        } catch (Exception e) {
            logger.error(tutorId);
            throw e;
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return tutor;
    }
}
