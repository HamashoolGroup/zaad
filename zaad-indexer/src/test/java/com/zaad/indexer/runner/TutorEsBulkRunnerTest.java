package com.zaad.indexer.runner;

import com.zaad.indexer.transport.ZaadEsClient;
import org.junit.Test;

public class TutorEsBulkRunnerTest extends AbstractBaseClientTest {

    @Test
    public void test() throws Exception {
        ZaadEsClient zaadEsClient = new ZaadTestEsTransportClient(client());
        TutorEsBulkRunner tutorEsBulkRunner = new TutorEsBulkRunner(zaadEsClient);
        tutorEsBulkRunner.bulk();
    }
}
