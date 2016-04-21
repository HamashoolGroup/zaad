package com.zaad.indexer.runner;

import com.zaad.indexer.transport.ZaadEsClient;
import com.zaad.indexer.transport.ZaadEsTransportClient;
import org.junit.Test;

import java.io.File;

public class VideoEsBulkRunnerTest extends AbstractBaseClientTest {

    @Test
    public void test() {
        ZaadEsClient zaadEsClient = new ZaadTestEsTransportClient(client());
        VideoEsBulkRunner videoEsBulkRunner = new VideoEsBulkRunner(zaadEsClient);
        videoEsBulkRunner.bulk();
    }

    @Test
    public void testPermission() throws Exception {
        File directory = new File("/home/lks21c/repo/crawl_new/data");
        File[] files = directory.listFiles();
    }
}
