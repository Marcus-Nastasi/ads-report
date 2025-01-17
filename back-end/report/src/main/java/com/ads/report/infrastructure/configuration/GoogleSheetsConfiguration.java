package com.ads.report.infrastructure.configuration;

import com.ads.report.infrastructure.gateway.GoogleSheetsRepoGateway;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

@Configuration
public class GoogleSheetsConfiguration {

    @Bean
    public Sheets googleSheetsService() throws IOException {
        URL resource = getClass().getClassLoader().getResource("credentials.json");
        if (resource == null) throw new FileNotFoundException("Credentials not found on 'resources/credentials.json'");
        try (InputStream inputStream = resource.openStream()) {
            GoogleCredentials credentials = GoogleCredentials
                .fromStream(inputStream)
                .createScoped(List.of("https://www.googleapis.com/auth/spreadsheets"));
            return new Sheets.Builder(
                new com.google.api.client.http.javanet.NetHttpTransport(),
                new GsonFactory(),
                new HttpCredentialsAdapter(credentials)
            ).setApplicationName("Ads Report").build();
        }
    }

    @Bean
    public GoogleSheetsRepoGateway googleSheetsRepoGateway() {
        return new GoogleSheetsRepoGateway();
    }
}
