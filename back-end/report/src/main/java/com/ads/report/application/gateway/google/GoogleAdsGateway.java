package com.ads.report.application.gateway.google;

import com.ads.report.domain.google.CampaignMetrics;

import java.util.List;

public interface GoogleAdsGateway {

    List<String> testConnection();

    List<CampaignMetrics> getCampaignMetrics(String customerId);
}
