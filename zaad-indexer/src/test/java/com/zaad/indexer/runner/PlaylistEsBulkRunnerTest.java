package com.zaad.indexer.runner;

import com.zaad.indexer.transport.ZaadEsClient;
import org.elasticsearch.action.search.SearchResponse;
import org.junit.Test;

public class PlaylistEsBulkRunnerTest extends AbstractBaseClientTest {

    @Test
    public void test() throws Exception {
        ZaadEsClient zaadEsClient = new ZaadTestEsTransportClient(client());
        PlaylistEsBulkRunner playlistEsBulkRunner = new PlaylistEsBulkRunner(zaadEsClient);
        playlistEsBulkRunner.bulk();

        SearchResponse searchResponse = client().prepareSearch("playlist_p").setTypes("detail").get();
        System.out.println("total hit = " + searchResponse.getHits().totalHits());
    }
}
