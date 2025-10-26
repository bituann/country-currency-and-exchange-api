package com.bituan.country_currency_and_exchange_api.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "countries")
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String capital;
    private String region;
    private Long population;
    private String currencyCode;
    private Double exchangeRate;
    private Double estimatedGdp;
    private String flagUrl;
    private Instant lastRefreshedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Double getEstimatedGdp() {
        return estimatedGdp;
    }

    public void setEstimatedGdp(Double estimatedGdp) {
        this.estimatedGdp = estimatedGdp;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }

    public Instant getLastRefreshedAt() {
        return lastRefreshedAt;
    }

    public void setLastRefreshedAt(Instant lastRefreshedAt) {
        this.lastRefreshedAt = lastRefreshedAt;
    }
}
