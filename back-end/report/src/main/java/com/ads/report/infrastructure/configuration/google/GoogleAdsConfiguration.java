package com.ads.report.infrastructure.configuration.google;

import com.ads.report.adapters.mappers.GoogleAdsDtoMapper;
import com.ads.report.application.gateway.google.GoogleAdsGateway;
import com.ads.report.application.usecases.GoogleAdsUseCase;
import com.ads.report.infrastructure.gateway.google.GoogleAdsRepoGateway;
import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.gson.Gson;
import com.opencsv.CSVWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Configuration
public class GoogleAdsConfiguration {

    @Bean
    public GoogleAdsClient googleAdsClient() throws IOException {
        URL resource = getClass().getClassLoader().getResource("ads.properties");
        if (resource == null) throw new IllegalArgumentException("File 'ads.properties' does not found on classpath");
        return GoogleAdsClient.newBuilder().fromPropertiesFile(new File(resource.getFile())).build();
    }

    @Bean
    public GoogleAdsGateway googleAdsGateway() {
        return new GoogleAdsRepoGateway();
    }

    @Bean
    public GoogleAdsUseCase googleAdsUseCase(GoogleAdsGateway googleAdsGateway) {
        return new GoogleAdsUseCase(googleAdsGateway);
    }

    @Bean
    public GoogleAdsDtoMapper googleAdsDtoMapper() {
        return new GoogleAdsDtoMapper();
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }
}
