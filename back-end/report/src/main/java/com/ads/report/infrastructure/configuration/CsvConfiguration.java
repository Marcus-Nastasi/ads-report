package com.ads.report.infrastructure.configuration;

import com.ads.report.application.gateway.JsonToCsvGateway;
import com.ads.report.application.usecases.JsonToCsvUseCase;
import com.ads.report.infrastructure.gateway.JsonToCsv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CsvConfiguration {

    @Bean
    public JsonToCsv jsonToCsvConverter() {
        return new JsonToCsv();
    }

    @Bean
    public JsonToCsvGateway jsonToCsvGateway() {
        return new JsonToCsv();
    }

    @Bean
    public JsonToCsvUseCase jsonToCsvUseCase(JsonToCsvGateway jsonToCsvGateway) {
        return new JsonToCsvUseCase(jsonToCsvGateway);
    }
}
