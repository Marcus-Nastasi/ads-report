package com.ads.report.adapters.resources;

import com.ads.report.adapters.mappers.GoogleAdsDtoMapper;
import com.ads.report.adapters.output.google.TestResponseDto;
import com.ads.report.application.usecases.GoogleAdsUseCase;
import com.ads.report.application.usecases.JsonToCsvUseCase;
import com.ads.report.domain.ManagerAccountInfo;
import com.ads.report.domain.AccountMetrics;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void getAllCampaignMetrics(@PathVariable String customerId, HttpServletResponse response) {
        String json = gson.toJson(googleAdsUseCase.getCampaignMetrics(customerId));
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
}
