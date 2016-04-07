package com.zaad.web.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaad.common.ZaadExecutionMode;
import com.zaad.common.util.ZaadProperties;

/**
 * @author lks21c
 */
@Configuration
public class EsClientConfig {
    // 간략히 설명하자면 커맨드라인, application.properties 순으로 의존성을 주입한다.
    // 참고 : https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html
	@Value("${es.cluster.name}")
	private String ES_CLUSTER_NAME;

	@Value("${es.host}")
	private String ES_HOST;

	@Value("${es.port}")
	private int ES_PORT;

    private Logger logger = LoggerFactory.getLogger(EsClientConfig.class);

	@Bean(destroyMethod = "close")
	public TransportClient transportClient() {
        logger.info("ES_CLUSTER_NAME = " + ES_CLUSTER_NAME);
        logger.info("ES_HOST = " + ES_HOST);
        logger.info("ES_PORT = " + ES_PORT);
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
