package com.ads.report.adapters.resources;

import com.ads.report.adapters.mappers.GoogleAdsDtoMapper;
import com.ads.report.adapters.output.google.TestResponseDto;
import com.ads.report.application.usecases.GoogleAdsUseCase;
import com.ads.report.application.usecases.GoogleSheetsUseCase;
import com.ads.report.application.usecases.JsonToCsvUseCase;
import com.ads.report.domain.ManagerAccountInfo;
import com.ads.report.domain.AccountMetrics;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * The controller to the application.
 *
 * <p>
 * This represents the controller of the application, making possible to request api calls.
 * To test request, you can request from route /api/reports and look at the routs bellow.
 * <p/>
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
@RestController
@RequestMapping("api/reports")
public class GoogleResource {

    @Autowired
    private GoogleAdsUseCase googleAdsUseCase;
    @Autowired
    private GoogleAdsDtoMapper googleAdsDtoMapper;
    @Autowired
    private GoogleSheetsUseCase googleSheetsUseCase;
    @Autowired
    private Gson gson;
    @Autowired
    private JsonToCsvUseCase jsonToCsv;

    /**
     * Endpoint to get All campaigns metrics.
     *
     * <p>
     * This method uses an algorithm and the CSVWriter library
     * to convert the JSON to CSV type.
     * <p/>
     *
     * @param customerId The id of an adwords customer (client).
     * @param response The response object.
     */
    @GetMapping("/campaign/{customerId}")
    public void getAllCampaignMetrics(
            @PathVariable String customerId,
            @PathParam("start_date") String start_date,
            @PathParam("end_date") String endDate,
            HttpServletResponse response) {
        String json = gson.toJson(googleAdsUseCase.getCampaignMetrics(customerId, start_date, endDate));
        String fileName = "campaigns-"+customerId+".csv";
        List<Map<String, Object>> records = gson.fromJson(json, new TypeToken<List<Map<String, Object>>>() {}.getType());
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + '"' + fileName + '"');
        jsonToCsv.convert(records, response);
    }

    /**
     * Endpoint to get aggregated metrics from one client account.
     *
     * @param customerId The id of an adwords customer (client).
     * @param start_date The start date of the analysis period.
     * @param end_date The end date of the analysis period.
     */
    @GetMapping("/account/metrics/{customerId}")
    public void getAccountMetrics(
            @PathVariable("customerId") String customerId,
            @PathParam("start_date") String start_date,
            @PathParam("end_date") String end_date,
            @PathParam("type") String type,
            HttpServletResponse response) {
        List<AccountMetrics> accountMetrics = googleAdsUseCase.getAccountMetrics(customerId, start_date, end_date);
        String json = gson.toJson(accountMetrics);
        String fileName = "account-metrics-"+accountMetrics.getFirst().getDescriptiveName()+"-"+start_date+"-"+end_date+".csv";
        List<Map<String, Object>> records = gson.fromJson(json, new TypeToken<List<Map<String, Object>>>() {}.getType());
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + '"' + fileName + '"');
        jsonToCsv.convert(records, response);
    }

    /**
     * Test the connection between the application and the google account.
     *
     * @return The TestResponseDto
     */
    @GetMapping("/test")
    public ResponseEntity<TestResponseDto> test() {
        return ResponseEntity.ok(googleAdsDtoMapper.mapToResponse(googleAdsUseCase.testConnection()));
    }

    /**
     * Recover the general data of the manager account specified.
     *
     * @param id The id of an adwords customer (client).
     * @return A object of type ManagerAccountInfo, that contains the general MCC info.
     */
    @GetMapping("/manager/{id}")
    public ResponseEntity<ManagerAccountInfo> getManagerAccount(@PathVariable("id") String id) {
        return ResponseEntity.ok(googleAdsUseCase.getManagerAccount(id));
    }

    /**
     * This method allows the user to send client account metrics directly from google ads to google sheets.
     *
     * <p>
     * Here the user can pass a adwords customer id, a start date, end date,
     * a google sheets id and tab, to send the data directly without needing
     * to download a csv.
     * <p/>
     *
     * @param customer_id The id of an adwords customer (client).
     * @param start_date The start date of the analysis period.
     * @param end_date The end date of the analysis period.
     * @param id The google sheets id.
     * @param tab The sheets tab to write.
     * @return Returns a response entity ok if successful.
     */
    @GetMapping("/sheets/account/{customer_id}")
    public ResponseEntity<String> writeToSheet(
            @PathVariable("customer_id") String customer_id,
            @PathParam("start_date") String start_date,
            @PathParam("end_date") String end_date,
            @PathParam("id") String id,
            @PathParam("tab") String tab) {
        try {
            googleSheetsUseCase.accountMetricsToSheets(id, tab, googleAdsUseCase.getAccountMetrics(customer_id, start_date, end_date));
        } catch (Exception e) {
            throw new RuntimeException("Unable to send data to sheets.");
        }
        return ResponseEntity.ok("");
    }

    /**
     * This method allows the user to send campaign metrics directly from google ads to google sheets.
     *
     * <p>
     * Here the user can pass a adwords customer id, a google sheets id and tab, to send the data
     * directly without needing to download a csv.
     * <p/>
     *
     * @param customer_id The id of an adwords customer (client).
     * @param id The google sheets id.
     * @param tab The sheets tab to write.
     * @return Returns a response entity ok if successful.
     */
    @GetMapping("/sheets/campaign/{customer_id}")
    public ResponseEntity<String> campaignMetricsToSheets(
            @PathVariable("customer_id") String customer_id,
            @PathParam("start_date") String start_date,
            @PathParam("end_date") String end_date,
            @PathParam("id") String id,
            @PathParam("tab") String tab) {
        try {
            googleSheetsUseCase.campaignMetricsToSheets(id, tab, googleAdsUseCase.getCampaignMetrics(customer_id, start_date, end_date));
        } catch (Exception e) {
            throw new RuntimeException("Unable to send data to sheets.");
        }
        return ResponseEntity.ok("");
    }
}
