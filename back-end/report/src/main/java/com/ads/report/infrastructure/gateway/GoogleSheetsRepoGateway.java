package com.ads.report.infrastructure.gateway;

import com.ads.report.application.gateway.GoogleSheetsGateway;
import com.ads.report.domain.AccountMetrics;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of google sheets interface.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class GoogleSheetsRepoGateway implements GoogleSheetsGateway {

    @Autowired
    private Sheets sheetsClient;

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
    @Override
    public void accountMetricsToSheets(String spreadsheetId, String tab, List<AccountMetrics> accountMetrics) throws IOException {
        List<List<Object>> sheetData = new ArrayList<>();
        sheetData.add(List.of("customerId", "descriptiveName", "impressions", "clicks", "cost", "conversions",	"ctr",	"averageCpc"));
        for (AccountMetrics obj : accountMetrics) {
            List<Object> row = List.of(
                obj.getCustomerId(),
                obj.getDescriptiveName(),
                obj.getImpressions(),
                obj.getClicks(),
                obj.getCost(),
                obj.getConversions(),
                obj.getCtr(),
                obj.getAverageCpc()
            );
            sheetData.add(row);
        }
        ValueRange body = new ValueRange().setValues(sheetData);
        tab = tab + "!A:Z";
        sheetsClient.spreadsheets().values()
            .update(spreadsheetId, tab, body)
            .setValueInputOption("RAW")
            .execute();
    }
}
