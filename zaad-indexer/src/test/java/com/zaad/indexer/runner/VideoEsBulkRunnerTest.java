package com.zaad.indexer.runner;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;

public class VideoEsBulkRunnerTest {

    @Test
    public void test() {
        Client client = new TransportClient.Builder().build();
        VideoEsBulkRunner videoEsBulkRunner = new VideoEsBulkRunner(client);
        videoEsBulkRunner.bulk();
    }

}
