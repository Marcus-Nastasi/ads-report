package com.ads.report.infrastructure.gateway.google;

import com.ads.report.application.gateway.google.GoogleAdsGateway;
import com.ads.report.domain.google.CampaignMetrics;
import com.ads.report.domain.google.ManagerAccountInfo;
import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.ads.googleads.v17.services.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class GoogleAdsRepoGateway implements GoogleAdsGateway {

    @Autowired
    private GoogleAdsClient googleAdsClient;

    @Override
    public List<String> testConnection() throws RuntimeException {
        // Create the CustomerService client
        try (CustomerServiceClient customerServiceClient = googleAdsClient.getLatestVersion().createCustomerServiceClient()) {
            final List<String> response = new ArrayList<>();
            // Creates request
            ListAccessibleCustomersRequest request = ListAccessibleCustomersRequest.newBuilder().build();
            // Call the API to list the accessible clients
            ListAccessibleCustomersResponse listed = customerServiceClient.listAccessibleCustomers(request);
            response.add("Successful connection.");
            response.addAll(listed.getResourceNamesList());
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Error while connecting with MCC: " + e.getMessage());
        }
    }

    @Override
    public List<CampaignMetrics> getCampaignMetrics(String customerId) {
        try (GoogleAdsServiceClient client = googleAdsClient.getLatestVersion().createGoogleAdsServiceClient()) {
            List<CampaignMetrics> campaignMetricsList = new ArrayList<>();
            String query = """
                SELECT
                    campaign.id,
                    campaign.name,
                    metrics.impressions,
                    metrics.clicks,
                    metrics.cost_micros,
                    metrics.conversions,
                    metrics.ctr,
                    metrics.average_cpc
                FROM
                    campaign
                WHERE
                    segments.date DURING LAST_7_DAYS
            """;
            SearchGoogleAdsRequest request = SearchGoogleAdsRequest.newBuilder()
                .setCustomerId(customerId)
                .setQuery(query)
                .build();
            client.search(request).iterateAll().forEach(row -> {
                CampaignMetrics campaignMetrics = new CampaignMetrics(
                        row.getCampaign().getId(),
                        row.getCampaign().getName(),
                        row.getMetrics().getImpressions(),
                        row.getMetrics().getClicks(),
                        row.getMetrics().getCostMicros() / 1_000_000.0,
                        row.getMetrics().getConversions(),
                        row.getMetrics().getCtr(),
                        row.getMetrics().getAverageCpc() / 1_000_000.0
                );
                campaignMetricsList.add(campaignMetrics);
            });
            return campaignMetricsList;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar métricas: " + e.getMessage());
        }
    }

    @Override
    public ManagerAccountInfo getManagerAccount(String managerAccountId) {
        try (GoogleAdsServiceClient client = googleAdsClient.getLatestVersion().createGoogleAdsServiceClient()) {
            String query = """
                SELECT customer.descriptive_name, customer.currency_code, customer.time_zone, customer.test_account
                FROM customer
            """;
            SearchGoogleAdsRequest request = SearchGoogleAdsRequest.newBuilder()
                .setCustomerId(managerAccountId)
                .setQuery(query)
                .build();
            GoogleAdsRow row = client.search(request).iterateAll().iterator().next(); // Espera apenas um resultado
            return new ManagerAccountInfo(
                row.getCustomer().getDescriptiveName(),
                row.getCustomer().getCurrencyCode(),
                row.getCustomer().getTimeZone(),
                row.getCustomer().getTestAccount()
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar informações da conta: " + e.getMessage(), e);
        }
    }
}
