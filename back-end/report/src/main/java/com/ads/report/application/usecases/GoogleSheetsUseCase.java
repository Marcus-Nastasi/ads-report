package com.ads.report.application.usecases;

import com.ads.report.application.gateway.GoogleSheetsGateway;
import com.ads.report.domain.AccountMetrics;

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

    public GoogleSheetsUseCase(GoogleSheetsGateway googleSheetsGateway) {
        this.googleSheetsGateway = googleSheetsGateway;
    }

    /**
     * This method allows the user to send data directly from google ads to google sheets.
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
}
