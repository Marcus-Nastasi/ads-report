package com.ads.report.application.usecases;

import com.ads.report.application.gateway.google.GoogleAdsGateway;
import com.ads.report.domain.campaign.CampaignMetrics;
import com.ads.report.domain.manager.ManagerAccountInfo;

import java.util.List;

public class GoogleAdsUseCase {

    private final GoogleAdsGateway googleAdsGateway;

    public GoogleAdsUseCase(GoogleAdsGateway googleAdsGateway) {
        this.googleAdsGateway = googleAdsGateway;
    }

    public List<CampaignMetrics> getCampaignMetrics(String customerId) throws RuntimeException {
        return googleAdsGateway.getCampaignMetrics(customerId);
    }

    public List<String> testConnection() throws RuntimeException {
        return googleAdsGateway.testConnection();
    }

    public ManagerAccountInfo getManagerAccount(String managerAccountId) {
        return googleAdsGateway.getManagerAccount(managerAccountId);
    }
}
