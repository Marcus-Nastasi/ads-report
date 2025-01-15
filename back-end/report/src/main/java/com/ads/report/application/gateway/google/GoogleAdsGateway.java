package com.ads.report.application.gateway.google;

import com.ads.report.domain.campaign.CampaignMetrics;
import com.ads.report.domain.manager.ManagerAccountInfo;
import com.ads.report.domain.metrics.AccountMetrics;

import java.util.List;

public interface GoogleAdsGateway {

    List<String> testConnection();

    List<CampaignMetrics> getCampaignMetrics(String customerId);

    ManagerAccountInfo getManagerAccount(String managerAccountId);

    List<AccountMetrics> getAccountMetrics(String customerId, String startDate, String endDate);
}
