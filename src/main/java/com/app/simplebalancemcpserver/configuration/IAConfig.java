package com.app.simplebalancemcpserver.configuration;

import com.app.simplebalancemcpserver.services.TransactionService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IAConfig {

    @Bean
    public ToolCallbackProvider transactionTool(TransactionService transactionService){
        return MethodToolCallbackProvider.builder()
                .toolObjects(transactionService)
                .build();
    }
}
