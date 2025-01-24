package com.ads.report.domain;

public class TotalPerDay {

    private String date;
    private long impressions;
    private long clicks;
    private double conversions;
    private double cost;
    private String campaignName;
    private String adName;
    private String researchKeyword;
    private String keyword;
    private int hour;
    private String dayOfWeek;

    public TotalPerDay(String date, long impressions, long clicks, double conversions, double cost, String campaignName, String adName, String researchKeyword, String keyword, int hour, String dayOfWeek) {
        this.date = date;
        this.impressions = impressions;
        this.clicks = clicks;
        this.conversions = conversions;
        this.cost = cost;
        this.campaignName = campaignName;
        this.adName = adName;
        this.researchKeyword = researchKeyword;
        this.keyword = keyword;
        this.hour = hour;
        this.dayOfWeek = dayOfWeek;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getImpressions() {
        return impressions;
    }

    public void setImpressions(long impressions) {
        this.impressions = impressions;
    }

    public long getClicks() {
        return clicks;
    }

    public void setClicks(long clicks) {
        this.clicks = clicks;
    }

    public double getConversions() {
        return conversions;
    }

    public void setConversions(double conversions) {
        this.conversions = conversions;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getResearchKeyword() {
        return researchKeyword;
    }

    public void setResearchKeyword(String researchKeyword) {
        this.researchKeyword = researchKeyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
