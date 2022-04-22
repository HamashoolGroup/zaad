package com.zaad.indexer.runner;

import com.zaad.indexer.transport.ZaadEsClient;
import org.elasticsearch.action.search.SearchResponse;
import org.junit.Test;

public class TutorEsBulkRunnerTest extends AbstractBaseClientTest {

    @Test
    public void test() throws Exception {
        ZaadEsClient zaadEsClient = new ZaadTestEsTransportClient(client());
        TutorEsBulkRunner tutorEsBulkRunner = new TutorEsBulkRunner(zaadEsClient);
        tutorEsBulkRunner.bulk();

        SearchResponse searchResponse = client().prepareSearch("tutor_p").setTypes("detail").get();
        System.out.println("total hit = " + searchResponse.getHits().totalHits());
    }
}
