package com.zaad.indexer.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaad.common.domain.Tutor;
import com.zaad.common.exception.ZaadDomainCreationException;
import com.zaad.common.util.ZaadLogger;
import com.zaad.common.util.ZaadOutputDirectoryManager;
import com.zaad.indexer.common.ZaadEsBulkRunner;
import com.zaad.indexer.sitemap.TutorSitemapGenerator;
import com.zaad.indexer.transport.ZaadEsClient;
import com.zaad.indexer.transport.ZaadEsTransportClient;
import org.apache.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TutorEsBulkRunner extends ZaadEsBulkRunner {
    protected static final int BULK_SIZE = 1000;
    private static final String INDEX_NAME = "tutor";
    private static final String TYPE_NAME = "detail";

    private static Logger logger;

    static {
        logger = ZaadLogger.getLogger(TutorEsBulkRunner.class);
    }

    public static void main(String[] args) {
        ZaadEsClient zaadEsClient = new ZaadEsTransportClient();
        TutorEsBulkRunner runner = new TutorEsBulkRunner(zaadEsClient);
        runner.bulk();
    }

    public TutorEsBulkRunner() {

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
        TutorSitemapGenerator sitemapGenerator = new TutorSitemapGenerator();
        for (String tutorId : tutorIds) {
            try {
                Tutor tutor = readTutor(tutorId);

                if (tutor != null) {
                    bulRequestBuilder.add(client.getClient().prepareIndex(newIndexName, typeName, tutor.getTutorId())
                            .setSource(mapper.writeValueAsString(tutor))
                    );

                    sitemapGenerator.appendUrl(tutor);
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

        sitemapGenerator.close();

        beforeExit(newIndexName, newIndexName, aliasName);
    }

    public static Tutor readTutor(String tutorId) throws Exception {
        File tutorDataFile = ZaadOutputDirectoryManager.getTutorDataFile(tutorId);
        Scanner scanner = null;
        String line = null;
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
            scanner.close();
        }

        return tutor;
    }
}
