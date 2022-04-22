package com.zaad.indexer.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaad.common.domain.Tutor;
import com.zaad.common.exception.ZaadDomainCreationException;
import com.zaad.common.util.ZaadOutputDirectoryManager;
import com.zaad.indexer.common.ZaadEsBulkRunner;
import com.zaad.indexer.sitemap.TutorSiteMapGenerator;
import com.zaad.indexer.transport.ZaadEsClient;
import com.zaad.indexer.transport.ZaadEsTransportClient;
import org.elasticsearch.action.index.IndexRequestBuilder;
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
     * 로거
     */
    private static Logger logger = LoggerFactory.getLogger(TutorEsBulkRunner.class);

    /**
     * 색인이름
     */
    private static final String INDEX_NAME = "tutor";

    /**
     * 타입이름
     */
    private static final String TYPE_NAME = "detail";

    public static void main(String[] args) throws InterruptedException {
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
    protected void bulk() throws InterruptedException {
        init();

        ObjectMapper mapper = new ObjectMapper();
        TutorSiteMapGenerator siteMapGenerator = new TutorSiteMapGenerator();

        List<String> tutorIds = ZaadOutputDirectoryManager.getDataTutorIds();
        for (String tutorId : tutorIds) {
            try {
                Tutor tutor = readTutor(tutorId);
                if (tutor != null) {
                    IndexRequestBuilder builder = this.client.getClient()
                            .prepareIndex(newIndexName, typeName) // 인덱스명 설정
                            .setId(tutor.getTutorId()) // 색인ID 설정
                            .setSource(mapper.writeValueAsString(tutor)); // 소스 설정
                    // 벌크 프로세서에 추가
                    processor.add(builder.request());
                    // 사이트맵에 주소 추가
                    siteMapGenerator.appendUrl(tutor);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        siteMapGenerator.close();

        // awaitClose시에 벌크 색인을 종료하기 전에 남아있는 색인 요청(BULK_SIZE보다 적은 요청)이 추가로 색인 실행
        // processor.awaitClose(..) 호출시 내부적으로 호출되므로 주석 처리함.
        //processor.flush();

        // 벌크 색인 종료를 기다림
        processor.close();

        /**
         * 1. alias 변경
         * 2. 색인 refresh
         */
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
