package com.zaad.indexer.transport;

import com.zaad.common.util.ZaadProperties;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 프로퍼티로부터 커넥션 정보를 읽어 TransportClient로 인스턴스 생성
 *
 * @author lks21c
 */
public class ZaadEsTransportClient implements ZaadEsClient {
    private static String ES_CLUSTER_NAME;
    private static String ES_HOST;
    private static int ES_PORT;

    static {
        ZaadProperties.loadProperties(ZaadEsTransportClientRunner.class.getClassLoader().getResourceAsStream("zaad.properties"));
        System.out.println(ZaadProperties.getAsString("es.cluster.name"));
        ES_CLUSTER_NAME = ZaadProperties.getAsString("es.cluster.name");
        ES_HOST = ZaadProperties.getAsString("es.host");
        ES_PORT = ZaadProperties.getAsInt("es.port");
    }

    @Override
    public Client getClient() {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", ES_CLUSTER_NAME)
                .build();

        Client client = null;
        try {
            client = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ES_HOST), ES_PORT))
            ;
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return client;
    }
}
