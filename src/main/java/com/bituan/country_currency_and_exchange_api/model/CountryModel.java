package com.bituan.country_currency_and_exchange_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryModel {
    private String name;
    private String capital;
    private String region;
    private Long population;
    private List<Map<String, String>> currencies;
    private String flagUrl;

    public CountryModel() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public List<Map<String, String>> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Map<String, String>> currencies) {
        this.currencies = currencies;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }
}
