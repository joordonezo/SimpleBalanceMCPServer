package com.app.simplebalancemcpserver.components;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ApiProperties {

    @Value("${app.apiUrl}")
    private String apiUrl;
}
