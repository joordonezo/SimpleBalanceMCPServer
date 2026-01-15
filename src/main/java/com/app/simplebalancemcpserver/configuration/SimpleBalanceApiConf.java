package com.app.simplebalancemcpserver.configuration;

import com.app.simplebalancemcpserver.components.ApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class SimpleBalanceApiConf {

    private final ApiProperties apiProperties;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(apiProperties.getApiUrl())
                .build();
    }
}
