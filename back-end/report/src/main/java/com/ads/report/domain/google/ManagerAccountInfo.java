package com.ads.report.domain.google;

import java.io.Serial;
import java.io.Serializable;

public class ManagerAccountInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String currency;
    private String timeZone;
    private boolean testAccount;

    public ManagerAccountInfo(String name, String currency, String timeZone, boolean testAccount) {
        this.name = name;
        this.currency = currency;
        this.timeZone = timeZone;
        this.testAccount = testAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public boolean isTestAccount() {
        return testAccount;
    }

    public void setTestAccount(boolean testAccount) {
        this.testAccount = testAccount;
    }
}
