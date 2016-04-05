package com.zaad.indexer.transport;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.zaad.common.ZaadExecutionMode;
import com.zaad.common.util.ZaadProperties;

public class ZaadEsTransportClientRunner {
	private static String ES_CLUSTER_NAME;
	private static String ES_HOST;
	private static int ES_PORT;
	
	protected Client client;
	
	static {
		ZaadProperties.loadProperties(ZaadEsTransportClientRunner.class.getClassLoader().getResourceAsStream("zaad" + ZaadExecutionMode.getEnvSuffix(System.getProperty("mode")) + ".properties"));
		ES_CLUSTER_NAME = ZaadProperties.getAsString("es.cluster.name");
		ES_HOST = ZaadProperties.getAsString("es.host");
		ES_PORT = ZaadProperties.getAsInt("es.port");
	}
	
	@SuppressWarnings("resource")
	public ZaadEsTransportClientRunner() {
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", ES_CLUSTER_NAME)
				.build()
		;
		
		this.client = new TransportClient(settings)
			.addTransportAddress(new InetSocketTransportAddress(ES_HOST, ES_PORT))
		;
	}
	
	public void close() {
		this.client.close();
	}
}
