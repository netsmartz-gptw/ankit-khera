package com.g1app.engine;

import com.g1app.engine.utils.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;



@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties({FileStorageProperties.class})
public class G1appEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(G1appEngineApplication.class, args);
	}

	@Bean
	public CommonsRequestLoggingFilter requestLoggingFilter() {
		CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
		loggingFilter.setIncludeClientInfo(true);
		loggingFilter.setIncludeQueryString(true);
		loggingFilter.setIncludePayload(true);
		loggingFilter.setIncludeHeaders(true);
		loggingFilter.setMaxPayloadLength(64000);
		return loggingFilter;
	}
}
