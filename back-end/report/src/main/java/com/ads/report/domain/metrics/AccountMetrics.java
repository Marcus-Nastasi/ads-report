package com.ads.report.domain.metrics;

import java.io.Serial;
import java.io.Serializable;

/**
 * The domain of the object that returns from the account metrics api call.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
public class AccountMetrics implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final long customerId;
    private final String descriptiveName;
    private final long impressions;
    private final long clicks;
    private final double cost;
    private final double conversions;
    private final double ctr;
    private final double averageCpc;

    public AccountMetrics(long customerId, String descriptiveName, long impressions, long clicks, double cost, double conversions, double ctr, double averageCpc) {
        this.customerId = customerId;
        this.descriptiveName = descriptiveName;
        this.impressions = impressions;
        this.clicks = clicks;
        this.cost = cost;
        this.conversions = conversions;
        this.ctr = ctr;
        this.averageCpc = averageCpc;
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getDescriptiveName() {
        return descriptiveName;
    }

    public long getImpressions() {
        return impressions;
    }

    public long getClicks() {
        return clicks;
    }

    public double getCost() {
        return cost;
    }

    public double getConversions() {
        return conversions;
    }

    public double getCtr() {
        return ctr;
    }

    public double getAverageCpc() {
        return averageCpc;
    }
}
