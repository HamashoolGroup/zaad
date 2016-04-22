package com.zaad.indexer.runner;

import com.zaad.indexer.transport.ZaadEsClient;
import org.elasticsearch.action.search.SearchResponse;
import org.junit.Test;

import java.io.File;

public class VideoEsBulkRunnerTest extends AbstractBaseClientTest {

    @Test
    public void test() throws Exception {
        ZaadEsClient zaadEsClient = new ZaadTestEsTransportClient(client());
        VideoEsBulkRunner videoEsBulkRunner = new VideoEsBulkRunner(zaadEsClient);
        videoEsBulkRunner.bulk();

        SearchResponse searchResponse = client().prepareSearch("video_p").setTypes("detail").get();
        System.out.println("total hit = " + searchResponse.getHits().totalHits());
        assertTrue(searchResponse.getHits().totalHits() > 0);
    }

    @Test
    public void testPermission() throws Exception {
        File directory = new File("/home/lks21c/repo/crawl_new/data");
        File[] files = directory.listFiles();
    }
}
