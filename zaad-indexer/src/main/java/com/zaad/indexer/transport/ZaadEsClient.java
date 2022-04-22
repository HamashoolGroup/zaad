package com.zaad.indexer.transport;

import org.elasticsearch.client.Client;

/**
 * 커넥션정보를 의존성 주입 받기 위한 인터페이스
 *
 * @author lks21c
 */
public interface ZaadEsClient {

    public Client getClient();
}
