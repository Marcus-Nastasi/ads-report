package com.ads.report.infrastructure.gateway;

import com.ads.report.application.gateway.GoogleAdsGateway;
import com.ads.report.domain.CampaignMetrics;
import com.ads.report.domain.ManagerAccountInfo;
import com.ads.report.domain.AccountMetrics;
import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.ads.googleads.v17.services.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of GoogleAdsGateway interface.
 * <p>
 * This class implements the interface contract with the goal of
 * requesting data from API, by using the google ads client.
 * <p/>
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class GoogleAdsRepoGateway implements GoogleAdsGateway {

    @Autowired
    private GoogleAdsClient googleAdsClient;

    /**
     * Test the connection with the adwords client.
     *
     * @return The status and a list of accessible customer accounts.
     * @throws RuntimeException If fails to connect.
     * */
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

    /**
     * Get campaigns and it's metrics.
     *
     * @param customerId The id of an adwords customer (client).
     *
     * @return The status and a list of CampaignMetrics objects.
     * @throws RuntimeException If fails to request the data.
     */
    @Override
    public List<CampaignMetrics> getCampaignMetrics(String customerId, String startDate, String endDate, boolean active) {
        // Connect to google ads service client
        try (GoogleAdsServiceClient client = googleAdsClient.getLatestVersion().createGoogleAdsServiceClient()) {
            String is_active = active ? "metrics.impressions > '0'" : "metrics.impressions >= '0'";
            List<CampaignMetrics> campaignMetricsList = new ArrayList<>();
            String query = String.format("""
                SELECT
                    campaign.id,
                    campaign.name,
                    campaign.status,
                    metrics.impressions,
                    metrics.clicks,
                    metrics.cost_micros,
                    metrics.conversions,
                    metrics.ctr,
                    metrics.average_cpc,
                    metrics.cost_per_conversion
                FROM campaign
                WHERE %s
                AND segments.date BETWEEN '%s' AND '%s'
                ORDER BY metrics.conversions DESC
            """, is_active, startDate, endDate);
            // Build a new request with the customerId and query
            SearchGoogleAdsRequest request = SearchGoogleAdsRequest.newBuilder()
                .setCustomerId(customerId)
                .setQuery(query)
                .build();
            // Iterating GoogleAdsRow objects to convert to CampaignMetrics
            for (GoogleAdsRow r: client.search(request).iterateAll()) {
                CampaignMetrics campaignMetrics = new CampaignMetrics(
                    r.getCampaign().getId(),
                    r.getCampaign().getName(),
                    r.getCampaign().getStatus().toString(),
                    r.getMetrics().getImpressions(),
                    r.getMetrics().getClicks(),
                    r.getMetrics().getCostMicros() / 1_000_000.0, // Converts micros to monetary units
                    r.getMetrics().getConversions(),             // Conversions total
                    r.getMetrics().getCtr(),                     // CTR (Click-through rate)
                    r.getMetrics().getAverageCpc() / 1_000_000.0, // CPC (convert to monetary units)
                    r.getMetrics().getCostPerConversion() / 1_000_000.0 // CPC (convert to monetary units)
                );
                campaignMetricsList.add(campaignMetrics);
            }
            return campaignMetricsList;
        } catch (Exception e) {
            throw new RuntimeException("Error searching metrics: " + e.getMessage());
        }
    }

    /**
     * Get general information of manager account.
     *
     * @param managerAccountId The id of an adwords customer (client).
     *
     * @return A ManagerAccountInfo type object.
     * @throws RuntimeException If fails to request the data.
     */
    @Override
    public ManagerAccountInfo getManagerAccount(String managerAccountId) {
        // Connect to google ads service client
        try (GoogleAdsServiceClient client = googleAdsClient.getLatestVersion().createGoogleAdsServiceClient()) {
            String query = """
                SELECT
                    customer.id,
                    customer.descriptive_name,
                    customer.currency_code,
                    customer.time_zone,
                    customer.test_account,
                    customer.status,
                    customer.manager,
                    customer.auto_tagging_enabled,
                    customer.tracking_url_template,
                    customer.final_url_suffix,
                    customer.conversion_tracking_setting.conversion_tracking_id,
                    customer.conversion_tracking_setting.conversion_tracking_status
                FROM customer
            """;
            // Build a new request with the customerId and query
            SearchGoogleAdsRequest request = SearchGoogleAdsRequest.newBuilder()
                .setCustomerId(managerAccountId)
                .setQuery(query)
                .build();
            GoogleAdsRow row = client.search(request).iterateAll().iterator().next(); // Expects unique return
            return new ManagerAccountInfo(
                String.valueOf(row.getCustomer().getId()),
                row.getCustomer().getDescriptiveName(),
                row.getCustomer().getCurrencyCode(),
                row.getCustomer().getTimeZone(),
                row.getCustomer().getTestAccount(),
                row.getCustomer().getStatus().name(),
                row.getCustomer().getManager(),
                row.getCustomer().getAutoTaggingEnabled(),
                row.getCustomer().hasTrackingUrlTemplate() ? row.getCustomer().getTrackingUrlTemplate() : null,
                row.getCustomer().hasFinalUrlSuffix() ? row.getCustomer().getFinalUrlSuffix() : null,
                row.getCustomer().getConversionTrackingSetting().hasConversionTrackingId() ? row.getCustomer().getConversionTrackingSetting().getConversionTrackingId() : null,
                row.getCustomer().getConversionTrackingSetting().getConversionTrackingStatus().name()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error searching account information: " + e.getMessage(), e);
        }
    }

    /**
     * Get general information of manager account.
     *
     * @param customerId The id of an adwords customer (client).
     * @param startDate The start date of the analysis period.
     * @param endDate The end date of the analysis period.
     *
     * @return A ManagerAccountInfo type object.
     * @throws RuntimeException If fails to request the data.
     */
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
                metrics.average_cpc,
                metrics.cost_per_conversion
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
                    r.getMetrics().getAverageCpc() / 1_000_000.0,
                    r.getMetrics().getCostPerConversion() / 1_000_000.0
                );
                accountMetricsList.add(accountMetrics);
            }
            return accountMetricsList;
        } catch (Exception e) {
            throw new RuntimeException("Error searching account metrics: " + e.getMessage(), e);
        }
    }
}
