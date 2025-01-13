package com.ads.report.application.usecases;

import com.ads.report.application.gateway.google.GoogleAdsGateway;
import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.ads.googleads.v17.services.*;

import java.util.ArrayList;
import java.util.List;

public class GoogleAdsUseCase {

    private final GoogleAdsClient googleAdsClient;
    private final GoogleAdsGateway googleAdsGateway;

    public GoogleAdsUseCase(GoogleAdsClient googleAdsClient, GoogleAdsGateway googleAdsGateway) {
        this.googleAdsClient = googleAdsClient;
        this.googleAdsGateway = googleAdsGateway;
    }

    public List<GoogleAdsRow> getCampaignMetrics(String customerId) throws RuntimeException {
        List<GoogleAdsRow> googleAdsRows = new ArrayList<>();
        try (GoogleAdsServiceClient client = googleAdsClient.getLatestVersion().createGoogleAdsServiceClient()) {
            String query = """
                SELECT campaign.id, campaign.name, metrics.impressions, metrics.clicks, metrics.cost_micros
                FROM campaign
                WHERE segments.date DURING LAST_7_DAYS
            """;
            SearchGoogleAdsRequest request = SearchGoogleAdsRequest.newBuilder()
                .setCustomerId(customerId)
                .setQuery(query)
                .build();
            client.search(request).iterateAll().forEach(googleAdsRows::add);
            return googleAdsRows;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar métricas: " + e.getMessage());
        }
    }

    public List<String> testConnection() throws RuntimeException {
        return googleAdsGateway.testConnection();
    }
}
