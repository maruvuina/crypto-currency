package com.idfinance.lab.cryptocurrency.configuration;

import com.idfinance.lab.cryptocurrency.client.WebClientProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
@EnableConfigurationProperties(WebClientProperty.class)
public class WebClientConfiguration {

    @Bean
    public RestTemplate webClient(WebClientProperty property) {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(property.getBaseUrl());
        restTemplate.setUriTemplateHandler(uriBuilderFactory);
        return restTemplate;
    }
}
