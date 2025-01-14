package com.ads.report.domain.google;

import java.io.Serial;
import java.io.Serializable;

public class CampaignMetrics implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long campaignId;
    private String campaignName;
    private Long impressions;
    private Long clicks;
    private Double cost;

    public CampaignMetrics(Long campaignId, String campaignName, Long impressions, Long clicks, Double cost) {
        this.campaignId = campaignId;
        this.campaignName = campaignName;
        this.impressions = impressions;
        this.clicks = clicks;
        this.cost = cost;
    }

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public Long getImpressions() {
        return impressions;
    }

    public void setImpressions(Long impressions) {
        this.impressions = impressions;
    }

    public Long getClicks() {
        return clicks;
    }

    public void setClicks(Long clicks) {
        this.clicks = clicks;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
