package com.ads.report.infrastructure.configuration.google;

import com.google.ads.googleads.lib.GoogleAdsClient;
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
        if (resource == null) throw new IllegalArgumentException("Arquivo ads.properties não encontrado no classpath");
        return GoogleAdsClient.newBuilder()
            .fromPropertiesFile(new File(resource.getFile()))
            .build();
    }
}
