package com.company.api.user.config;

import com.company.api.user.sync.RestTemplateResponseErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    RestTemplate restTemplate(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .build();
    }
}
