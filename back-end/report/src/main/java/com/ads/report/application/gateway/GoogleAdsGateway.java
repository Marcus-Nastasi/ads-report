package com.ads.report.application.gateway;

import com.ads.report.domain.CampaignMetrics;
import com.ads.report.domain.ManagerAccountInfo;
import com.ads.report.domain.AccountMetrics;
import com.ads.report.domain.TotalPerDay;

import java.util.List;

/**
 * The interface of the google ads api calls.
 *
 * <p>
 * This represents the interface that communicates the application with the infra layer,
 * making possible to make api calls.
 * <p/>
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public interface GoogleAdsGateway {

    /**
     * Test the connection with the adwords client.
     *
     * @return The status and a list of accessible customer accounts.
     * @throws RuntimeException If fails to connect.
     * */
    List<String> testConnection();

    /**
     * Get campaigns and it's metrics.
     *
     * @param customerId The id of an adwords customer (client).
     *
     * @return The status and a list of CampaignMetrics objects.
     * @throws RuntimeException If fails to request the data.
     */
    List<CampaignMetrics> getCampaignMetrics(String customerId, String startDate, String endDate, boolean active);

    /**
     * Get general information of manager account.
     *
     * @param managerAccountId The id of an adwords customer (client).
     *
     * @return A ManagerAccountInfo type object.
     * @throws RuntimeException If fails to request the data.
     */
    ManagerAccountInfo getManagerAccount(String managerAccountId);

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
    List<AccountMetrics> getAccountMetrics(String customerId, String startDate, String endDate);

    /**
     * This method allows the user to send client account metrics, separated per days,
     * directly from google ads to google sheets.
     *
     * @param customerId The id of an adwords customer (client).
     * @param startDate The start date of the analysis period.
     * @param endDate The end date of the analysis period.
     * @return Returns a list of TotalPerDay object.
     */
    List<TotalPerDay> getTotalPerDay(String customerId, String startDate, String endDate);
}
