package com.ads.report.infrastructure.gateway.google;

import com.ads.report.application.gateway.google.GoogleAdsGateway;
import com.ads.report.domain.campaign.CampaignMetrics;
import com.ads.report.domain.manager.ManagerAccountInfo;
import com.ads.report.domain.metrics.AccountMetrics;
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
        // Create the CustomerServiceClient
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
            throw new RuntimeException("Failed to connect to MCC: " + e.getMessage());
        }
    }

    @Override
    public List<CampaignMetrics> getCampaignMetrics(String customerId) {
        // Connect to google ads service client
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
            // Build a new request with the customerId and query
            SearchGoogleAdsRequest request = SearchGoogleAdsRequest.newBuilder()
                .setCustomerId(customerId)
                .setQuery(query)
                .build();
            // Iterating GoogleAdsRow objects to convert to CampaignMetrics
            client.search(request).iterateAll().forEach(row -> {
                CampaignMetrics campaignMetrics = new CampaignMetrics(
                    row.getCampaign().getId(),
                    row.getCampaign().getName(),
                    row.getMetrics().getImpressions(),
                    row.getMetrics().getClicks(),
                    row.getMetrics().getCostMicros() / 1_000_000.0, // Converts micros to monetary units
                    row.getMetrics().getConversions(),             // Conversions total
                    row.getMetrics().getCtr(),                     // CTR (Click-through rate)
                    row.getMetrics().getAverageCpc() / 1_000_000.0 // CPC (convert to monetary units)
                );
                campaignMetricsList.add(campaignMetrics);
            });
            return campaignMetricsList;
        } catch (Exception e) {
            throw new RuntimeException("Error searching metrics: " + e.getMessage());
        }
    }

    @Override
    public ManagerAccountInfo getManagerAccount(String managerAccountId) {
        // Connect to google ads service client
        try (GoogleAdsServiceClient client = googleAdsClient.getLatestVersion().createGoogleAdsServiceClient()) {
            String query = """
                SELECT
                    customer.descriptive_name,
                    customer.currency_code,
                    customer.time_zone,
                    customer.test_account
                FROM customer
            """;
            // Build a new request with the customerId and query
            SearchGoogleAdsRequest request = SearchGoogleAdsRequest.newBuilder()
                .setCustomerId(managerAccountId)
                .setQuery(query)
                .build();
            GoogleAdsRow row = client.search(request).iterateAll().iterator().next(); // Expects unique return
            return new ManagerAccountInfo(
                row.getCustomer().getDescriptiveName(),
                row.getCustomer().getCurrencyCode(),
                row.getCustomer().getTimeZone(),
                row.getCustomer().getTestAccount()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error searching account information: " + e.getMessage(), e);
        }
    }

    @Override
    public List<AccountMetrics> getAccountMetrics(String customerId, String startDate, String endDate) {
        List<AccountMetrics> accountMetricsList = new ArrayList<>();
        String query = String.format("""
            SELECT
                customer.id,
                customer.descriptive_name,
                metrics.impressions,
                metrics.clicks,
                metrics.cost_micros,
                metrics.conversions,
                metrics.ctr,
                metrics.average_cpc
            FROM customer
            WHERE segments.date BETWEEN '%s' AND '%s'
        """, startDate, endDate);
        try (GoogleAdsServiceClient client = googleAdsClient.getLatestVersion().createGoogleAdsServiceClient()) {
            // Build a new request with the customerId and query
            SearchGoogleAdsRequest request = SearchGoogleAdsRequest.newBuilder()
                .setCustomerId(customerId)
                .setQuery(query)
                .build();
            // Iterating GoogleAdsRow objects to convert to CampaignMetrics
            for (GoogleAdsRow r: client.search(request).iterateAll()) {
                AccountMetrics accountMetrics = new AccountMetrics(
                    r.getCustomer().getId(),
                    r.getCustomer().getDescriptiveName(),
                    r.getMetrics().getImpressions(),
                    r.getMetrics().getClicks(),
                    r.getMetrics().getCostMicros() / 1_000_000.0,
                    r.getMetrics().getConversions(),
                    r.getMetrics().getCtr(),
                    r.getMetrics().getAverageCpc() / 1_000_000.0
                );
                accountMetricsList.add(accountMetrics);
            }
            return accountMetricsList;
        } catch (Exception e) {
            throw new RuntimeException("Error searching account metrics: " + e.getMessage(), e);
        }
    }
}
