package com.zaad.web.config;

import org.apache.catalina.connector.Connector;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.ProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;

/**
 * @author lks21c
 */
@Configuration
public class TomcatConfig {
	@Value("${tomcat.protocol}")
	private String TOMCAT_PROTOCOL;

	@Value("${server.port}")
	private int TOMCAT_PORT;

	private Logger logger = LoggerFactory.getLogger(TomcatConfig.class);

	/**
	 * 톰캣 환경 설정용 Bean
	 */
	@Bean
	protected EmbeddedServletContainerFactory tomcatFactoryBean() {
		TomcatEmbeddedServletContainerFactory factory;

		// 톰캣 포트 설정, 인자로 입력
		factory = new TomcatEmbeddedServletContainerFactory(TOMCAT_PORT);

		// DEV 모드시 Http11NioProtocol, 그 외에는 AjpNioProtocol 프로토콜로 톰캣 커넥터 실행
		if ("http".equals(TOMCAT_PROTOCOL)) {
			logger.info("Http11NioProtocol started");
			factory.setProtocol(TomcatEmbeddedServletContainerFactory.DEFAULT_PROTOCOL);
		} else {
			logger.info("AjpNioProtocol started");
			factory.setProtocol("org.apache.coyote.ajp.AjpNioProtocol");
		}

		// URI 인코딩 설정
		factory.setUriEncoding(Charset.forName("UTF-8"));

		// 톰캣설정 : maxThreads="2048", processorCache="2048", acceptorThreadCount="2"
		factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
			@Override
			public void customize(Connector connector) {
				ProtocolHandler handler = connector.getProtocolHandler();
				if (handler instanceof AbstractProtocol) {
					@SuppressWarnings("rawtypes")
					AbstractProtocol protocol = (AbstractProtocol) handler;
					protocol.setPort(TOMCAT_PORT);
					protocol.setMaxThreads(2048);
					protocol.setProcessorCache(2048);
					protocol.setProperty("acceptorThreadCount", "2");
				}
			}
		});
		return factory;
	}
}
