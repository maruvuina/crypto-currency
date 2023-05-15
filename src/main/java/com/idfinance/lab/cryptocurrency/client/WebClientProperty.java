package com.idfinance.lab.cryptocurrency.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "cryptocurrency.web-client")
public class WebClientProperty {

    private String baseUrl;
}
