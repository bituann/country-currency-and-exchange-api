package com.bituan.country_currency_and_exchange_api.model;

import java.time.Instant;

public class StatusResponseModel {
    private long totalCountries;
    private Instant lastRefreshedAt;

    public StatusResponseModel() {}

    public long getTotalCountries() {
        return totalCountries;
    }

    public void setTotalCountries(long totalCountries) {
        this.totalCountries = totalCountries;
    }

    public Instant getLastRefreshedAt() {
        return lastRefreshedAt;
    }

    public void setLastRefreshedAt(Instant lastRefreshedAt) {
        this.lastRefreshedAt = lastRefreshedAt;
    }
}
