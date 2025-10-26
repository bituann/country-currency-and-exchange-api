package com.bituan.country_currency_and_exchange_api.model;

public class FilterParamModel {
    private String region;
    private String currency;
    private Double gdp;
    private String sortBy;

    public FilterParamModel() {}

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency.toUpperCase();
    }

    public Double getGdp() {
        return gdp;
    }

    public void setGdp(Double gdp) {
        this.gdp = Double.valueOf("%.1f".formatted(gdp));
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
