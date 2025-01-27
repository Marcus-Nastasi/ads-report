package com.ads.report.application.usecases;

import com.ads.report.application.gateway.GoogleSheetsGateway;
import com.ads.report.domain.AccountMetrics;
import com.ads.report.domain.CampaignMetrics;
import com.ads.report.domain.KeywordMetrics;
import com.ads.report.domain.TotalPerDay;

import java.io.IOException;
import java.util.List;

/**
 * The use cases of google sheets.
 *
 * <p>
 * This represents all the google sheets use cases.
 * <p/>
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class GoogleSheetsUseCase {

    private final GoogleSheetsGateway googleSheetsGateway;

    /**
     * Constructor of google sheets use case class.
     *
     * @param googleSheetsGateway the interface contract of google sheets available methods.
     */
    public GoogleSheetsUseCase(GoogleSheetsGateway googleSheetsGateway) {
        this.googleSheetsGateway = googleSheetsGateway;
    }

    /**
     * This method allows the user to send account metrics directly from google ads to google sheets.
     *
     * <p>
     * Here the user can pass a adwords customer id, a start date, end date,
     * a google sheets id and tab, to send the data directly without needing
     * to download a csv.
     * <p/>
     *
     * @param spreadsheetId The google sheets id.
     * @param tab The sheets tab to write.
     * @param accountMetrics A list of AccountMetrics object.
     * @throws IOException Throws exception if fails.
     */
    public void accountMetricsToSheets(String spreadsheetId, String tab, List<AccountMetrics> accountMetrics) throws IOException {
        googleSheetsGateway.accountMetricsToSheets(spreadsheetId, tab, accountMetrics);
    }

    /**
     * This method allows the user to send campaign metrics directly from google ads to google sheets.
     *
     * <p>
     * Here the user can pass a adwords customer id, a start date, end date,
     * a google sheets id and tab, to send the data directly without needing
     * to download a csv.
     * <p/>
     *
     * @param spreadsheetId The google sheets id.
     * @param tab The sheets tab to write.
     * @param campaignMetrics A list of AccountMetrics object.
     */
    public void campaignMetricsToSheets(String spreadsheetId, String tab, List<CampaignMetrics> campaignMetrics) throws IOException {
        googleSheetsGateway.campaignMetricsToSheets(spreadsheetId, tab, campaignMetrics);
    }

    /**
     * This method allows the user to send client account metrics, separated per days,
     * directly from google ads to google sheets.
     *
     * <p>
     * Here the user can pass a adwords customer id, a start date, end date,
     * a spreadsheet id and tab, to send metrics per day directly without needing
     * to download a csv.
     * <p/>
     *
     * @param spreadsheetId The google sheets id.
     * @param tab The sheets tab to write.
     * @param totalPerDays the list of TotalPerDay objects.
     * @throws IOException throws IOException if fails.
     */
    public void totalPerDaysToSheet(String spreadsheetId, String tab, List<TotalPerDay> totalPerDays) throws IOException {
        googleSheetsGateway.totalPerDayToSheets(spreadsheetId, tab, totalPerDays);
    }

    /**
     * This method allows the user to send keyword metrics to sheets.
     *
     * @param spreadsheetId The google sheets id.
     * @param tab The sheets tab to write.
     * @param keywordMetrics the list of TotalPerDay objects.
     * @throws IOException throws IOException if fails.
     */
    public void sendKeywordMetrics(String spreadsheetId, String tab, List<KeywordMetrics> keywordMetrics) throws IOException {
        googleSheetsGateway.sendKeywordMetrics(spreadsheetId, tab, keywordMetrics);
    }
}
