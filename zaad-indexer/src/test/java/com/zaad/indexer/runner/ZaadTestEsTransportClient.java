package com.zaad.indexer.runner;

import com.zaad.indexer.transport.ZaadEsClient;
import org.elasticsearch.client.Client;

/**
 * Created by lks21c on 16. 4. 19.
 */
public class ZaadTestEsTransportClient implements ZaadEsClient {

    private Client client;

    public ZaadTestEsTransportClient(Client client) {
        this.client = client;
    }

    @Override
    public Client getClient() {
        return this.client;
    }
}
