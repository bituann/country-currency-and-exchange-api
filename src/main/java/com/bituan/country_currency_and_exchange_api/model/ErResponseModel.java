package com.bituan.country_currency_and_exchange_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErResponseModel {
    private Map<String, Integer> rates;

    public Map<String, Integer> getRates() {
        return rates;
    }

    public void setRates(Map<String, Integer> rates) {
        this.rates = rates;
    }
}
