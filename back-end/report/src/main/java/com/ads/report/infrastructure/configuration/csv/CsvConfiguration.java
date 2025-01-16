package com.ads.report.infrastructure.configuration.csv;

import com.ads.report.infrastructure.gateway.csv.JsonToCsvConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CsvConfiguration {

    @Bean
    public JsonToCsvConverter jsonToCsvConverter() {
        return new JsonToCsvConverter();
    }
}
