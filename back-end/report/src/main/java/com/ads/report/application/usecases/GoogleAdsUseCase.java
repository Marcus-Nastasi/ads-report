package com.ads.report.application.usecases;

import com.ads.report.application.gateway.GoogleAdsGateway;
import com.ads.report.domain.CampaignMetrics;
import com.ads.report.domain.ManagerAccountInfo;
import com.ads.report.domain.AccountMetrics;

import java.util.List;

/**
 * The use cases of google ads api calls.
 * <p>
 * This class uses the interface contract to call the implementations.
 * <p/>
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class GoogleAdsUseCase {

    private final GoogleAdsGateway googleAdsGateway;

    public GoogleAdsUseCase(GoogleAdsGateway googleAdsGateway) {
        this.googleAdsGateway = googleAdsGateway;
    }

    /**
     * Get campaigns and it's metrics.
     *
     * @param customerId The id of an adwords customer (client).
     *
     * @return The status and a list of CampaignMetrics objects.
     * @throws RuntimeException If fails to request the data.
     */
    public List<CampaignMetrics> getCampaignMetrics(String customerId, String startDate, String endDate) throws RuntimeException {
        return googleAdsGateway.getCampaignMetrics(customerId, startDate, endDate);
    }

    /**
     * Test the connection with the adwords client.
     *
     * @return The status and a list of accessible customer accounts.
     * @throws RuntimeException If fails to connect.
     * */
    public List<String> testConnection() throws RuntimeException {
        return googleAdsGateway.testConnection();
    }

    /**
     * Get general information of manager account.
     *
     * @param managerAccountId The id of an adwords customer (client).
     *
     * @return A ManagerAccountInfo type object.
     * @throws RuntimeException If fails to request the data.
     */
    public ManagerAccountInfo getManagerAccount(String managerAccountId) {
        return googleAdsGateway.getManagerAccount(managerAccountId);
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
    public List<AccountMetrics> getAccountMetrics(String customerId, String startDate, String endDate) {
        return googleAdsGateway.getAccountMetrics(customerId, startDate, endDate);
    }
}
