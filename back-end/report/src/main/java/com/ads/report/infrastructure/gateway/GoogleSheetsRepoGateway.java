package com.ads.report.infrastructure.gateway;

import com.ads.report.application.gateway.GoogleSheetsGateway;
import com.ads.report.domain.AccountMetrics;
import com.ads.report.domain.CampaignMetrics;
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
    @Override
    public void accountMetricsToSheets(String spreadsheetId, String tab, List<AccountMetrics> accountMetrics) throws IOException {
        List<List<Object>> sheetData = new ArrayList<>();
        sheetData.add(
            List.of("customerId", "descriptiveName", "impressions", "clicks", "cost", "conversions",	"ctr",	"averageCpc")
        );
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

    /**
     * This method allows the user to send campaign metrics directly from google ads to google sheets.
     *
     * <p>
     * Here the user can pass a adwords customer id, a google sheets id and tab, to send the data
     * directly without needing to download a csv.
     * <p/>
     *
     * @param spreadsheetId The id of an adwords customer (client).
     * @param tab The sheets tab to write.
     * @param campaignMetrics A list of CampaignMetrics object.
     * @throws IOException if fails to send data.
     */
    @Override
    public void campaignMetricsToSheets(String spreadsheetId, String tab, List<CampaignMetrics> campaignMetrics) throws IOException {
        List<List<Object>> sheetData = new ArrayList<>();
        sheetData.add(List.of("campaignId", "campaignName", "impressions", "clicks", "cost", "conversions", "ctr", "averageCpc"));
        for (CampaignMetrics obj : campaignMetrics) {
            List<Object> row = List.of(
                obj.getCampaignId(),
                obj.getCampaignName(),
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
