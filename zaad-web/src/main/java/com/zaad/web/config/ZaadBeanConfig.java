package com.zaad.web.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaad.common.ZaadExecutionMode;
import com.zaad.common.util.ZaadProperties;

@Configuration
public class ZaadBeanConfig {
	private static String ES_CLUSTER_NAME = "local";
	private static String ES_HOST = "localhost";
	private static int ES_PORT = 9300;

	@Bean(destroyMethod = "close")
	public TransportClient transportClient() {
		Settings settings = Settings.settingsBuilder()
				.put("cluster.name", ES_CLUSTER_NAME)
				.build()
		;
		
		try {
			return TransportClient.builder().settings(settings).build()
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ES_HOST), ES_PORT))
			;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
