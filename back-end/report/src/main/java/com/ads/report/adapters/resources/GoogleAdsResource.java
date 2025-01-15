package com.ads.report.adapters.resources;

import com.ads.report.adapters.mappers.GoogleAdsDtoMapper;
import com.ads.report.adapters.output.google.TestResponseDto;
import com.ads.report.application.usecases.GoogleAdsUseCase;
import com.ads.report.domain.manager.ManagerAccountInfo;
import com.ads.report.domain.metrics.AccountMetrics;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * The controller to the application.
 * <p>
 * This represents the controller of the application, making possible to request api calls.
 * <p/>
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
@RestController
@RequestMapping("api/reports")
public class GoogleAdsResource {

    @Autowired
    private GoogleAdsUseCase googleAdsUseCase;
    @Autowired
    private GoogleAdsDtoMapper googleAdsDtoMapper;
    @Autowired
    private Gson gson;

    /**
     * Endpoint to get All campaigns metrics.
     *
     * <p>
     * This method uses an algorithm and the CSVWriter library
     * to convert the JSON to CSV type.
     * <p/>
     *
     * @param customerId The id of an adwords customer (client).
     * @param response The HttpServletResponse object associated with the response.
     */
    @GetMapping("/campaign/{customerId}")
    public void getAllCampaignMetrics(@PathVariable String customerId, HttpServletResponse response) {
        String json = gson.toJson(googleAdsUseCase.getCampaignMetrics(customerId));
        List<Map<String, Object>> records = gson.fromJson(json, new TypeToken<List<Map<String, Object>>>() {}.getType());
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"campaigns.csv\"");
        try (CSVWriter writer = new CSVWriter(response.getWriter())) {
            if (!records.isEmpty()) {
                String[] headers = records.getFirst().keySet().toArray(new String[0]);
                writer.writeNext(headers);
            }
            for (Map<String, Object> record : records) {
                String[] data = record.values().stream().map(String::valueOf).toArray(String[]::new);
                writer.writeNext(data);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Endpoint to get aggregated metrics from one client account.
     *
     * @param costumerId The id of an adwords customer (client).
     * @param start_date The start date of the analysis period.
     * @param end_date The end date of the analysis period.
     * @return A ResponseEntity of type List of AccountMetrics.
     */
    @GetMapping("/account/metrics/{customerId}")
    public ResponseEntity<List<AccountMetrics>> getAccountMetrics(
            @PathVariable("customerId") String costumerId,
            @PathParam("start_date") String start_date,
            @PathParam("end_date") String end_date) {
        return ResponseEntity.ok(googleAdsUseCase.getAccountMetrics(costumerId, start_date, end_date));
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
}
