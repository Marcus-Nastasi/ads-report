package com.ads.report.adapters.resources;

import com.ads.report.application.usecases.GoogleAdsUseCase;
import com.google.ads.googleads.v17.services.GoogleAdsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/reports")
public class GoogleAdsResource {

    @Autowired
    private GoogleAdsUseCase googleAdsUseCase;

    @GetMapping()
    public ResponseEntity<List<GoogleAdsRow>> getAll() {
        return ResponseEntity.ok(googleAdsUseCase.getCampaignMetrics());
    }

    @GetMapping("/test-app")
    public ResponseEntity<String> testApp() {
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/test")
    public ResponseEntity<List<String>> test() {
        return ResponseEntity.ok(googleAdsUseCase.testConnection());
    }
}
