package com.zaad.indexer.runner;

import com.zaad.indexer.transport.ZaadEsClient;
import com.zaad.indexer.transport.ZaadEsTransportClient;
import org.junit.Test;

public class VideoEsBulkRunnerTest {

    @Test
    public void test() {
        ZaadEsClient zaadEsClient = new ZaadEsTransportClient();
        VideoEsBulkRunner videoEsBulkRunner = new VideoEsBulkRunner(zaadEsClient);
        videoEsBulkRunner.bulk();
    }

}
