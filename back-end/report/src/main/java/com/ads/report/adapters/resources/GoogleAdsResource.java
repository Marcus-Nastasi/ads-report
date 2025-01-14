package com.ads.report.adapters.resources;

import com.ads.report.adapters.mappers.GoogleAdsDtoMapper;
import com.ads.report.adapters.output.google.TestResponseDto;
import com.ads.report.application.usecases.GoogleAdsUseCase;
import com.ads.report.domain.google.CampaignMetrics;
import com.ads.report.domain.google.ManagerAccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/reports")
public class GoogleAdsResource {

    @Autowired
    private GoogleAdsUseCase googleAdsUseCase;
    @Autowired
    private GoogleAdsDtoMapper googleAdsDtoMapper;

    @GetMapping("/test-app")
    public ResponseEntity<Map<String, String>> testApp() {
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<CampaignMetrics>> getAll(@PathVariable String customerId) {
        return ResponseEntity.ok(googleAdsUseCase.getCampaignMetrics(customerId));
    }

    @GetMapping("/test")
    public ResponseEntity<TestResponseDto> test() {
        return ResponseEntity.ok(googleAdsDtoMapper.mapToResponse(googleAdsUseCase.testConnection()));
    }

    @GetMapping("/manager/{id}")
    public ResponseEntity<ManagerAccountInfo> getManagerAccount(@PathVariable("id") String id) {
        return ResponseEntity.ok(googleAdsUseCase.getManagerAccount(id));
    }
}
