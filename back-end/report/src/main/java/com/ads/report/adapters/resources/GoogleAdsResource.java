package com.ads.report.adapters.resources;

import com.ads.report.adapters.mappers.GoogleAdsDtoMapper;
import com.ads.report.adapters.output.google.TestResponseDto;
import com.ads.report.application.usecases.GoogleAdsUseCase;
import com.ads.report.domain.campaign.CampaignMetrics;
import com.ads.report.domain.manager.ManagerAccountInfo;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/reports")
public class GoogleAdsResource {

    @Autowired
    private GoogleAdsUseCase googleAdsUseCase;
    @Autowired
    private GoogleAdsDtoMapper googleAdsDtoMapper;
    @Autowired
    private Gson gson;

    @GetMapping("/test")
    public ResponseEntity<TestResponseDto> test() {
        return ResponseEntity.ok(googleAdsDtoMapper.mapToResponse(googleAdsUseCase.testConnection()));
    }

    @GetMapping("/manager/{id}")
    public ResponseEntity<ManagerAccountInfo> getManagerAccount(@PathVariable("id") String id) {
        return ResponseEntity.ok(googleAdsUseCase.getManagerAccount(id));
    }

    @GetMapping("/campaign/{customerId}")
    public void getAllCampaignMetrics(@PathVariable String customerId, HttpServletResponse response) {
        List<CampaignMetrics> campaignMetrics = googleAdsUseCase.getCampaignMetrics(customerId);
        String json = gson.toJson(campaignMetrics);
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
}
