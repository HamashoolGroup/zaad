package com.zaad.indexer.transport;

import com.zaad.common.util.ZaadProperties;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ZaadEsTransportClientRunner {
	private static String ES_CLUSTER_NAME;
	private static String ES_HOST;
	private static int ES_PORT;
	
	protected Client client;
	
	static {
		ZaadProperties.loadProperties(ZaadEsTransportClientRunner.class.getClassLoader().getResourceAsStream("zaad.properties"));
		ES_CLUSTER_NAME = ZaadProperties.getAsString("es.cluster.name");
		ES_HOST = ZaadProperties.getAsString("es.host");
		ES_PORT = ZaadProperties.getAsInt("es.port");
	}
	
	public ZaadEsTransportClientRunner() {
		Settings settings = Settings.settingsBuilder()
				.put("cluster.name", ES_CLUSTER_NAME)
				.build()
		;

		System.out.println("ZaadEsTransportClientRunner");

		try {
			this.client = TransportClient.builder().settings(settings).build()
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ES_HOST), ES_PORT))
			;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() {
		this.client.close();
	}
}
