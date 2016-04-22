package com.zaad.indexer.runner;

import com.zaad.indexer.transport.ZaadEsClient;
import org.junit.Test;

public class PlaylistEsBulkRunnerTest extends AbstractBaseClientTest {

    @Test
    public void test() throws Exception {
        ZaadEsClient zaadEsClient = new ZaadTestEsTransportClient(client());
        PlaylistEsBulkRunner playlistEsBulkRunner = new PlaylistEsBulkRunner(zaadEsClient);
        playlistEsBulkRunner.bulk();
    }
}
