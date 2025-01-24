package com.ads.report.domain;

public class KeywordMetrics {

    private String date;
    private String campaignName;
    private String adGroupName;
    private String keywordText;
    private String matchType;
    private Long impressions;
    private Long clicks;
    private double cost;
    private double averageCpc;
    private double conversions;
    private String conversionRate;

    public KeywordMetrics(String date, String campaignName, String adGroupName, String keywordText, String matchType, Long impressions, Long clicks, double cost, double averageCpc, double conversions, String conversionRate) {
        this.date = date;
        this.campaignName = campaignName;
        this.adGroupName = adGroupName;
        this.keywordText = keywordText;
        this.matchType = matchType;
        this.impressions = impressions;
        this.clicks = clicks;
        this.cost = cost;
        this.averageCpc = averageCpc;
        this.conversions = conversions;
        this.conversionRate = conversionRate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getAdGroupName() {
        return adGroupName;
    }

    public void setAdGroupName(String adGroupName) {
        this.adGroupName = adGroupName;
    }

    public String getKeywordText() {
        return keywordText;
    }

    public void setKeywordText(String keywordText) {
        this.keywordText = keywordText;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getAverageCpc() {
        return averageCpc;
    }

    public void setAverageCpc(double averageCpc) {
        this.averageCpc = averageCpc;
    }

    public double getConversions() {
        return conversions;
    }

    public void setConversions(double conversions) {
        this.conversions = conversions;
    }

    public String getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(String conversionRate) {
        this.conversionRate = conversionRate;
    }
}
