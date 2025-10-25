package com.bituan.country_currency_and_exchange_api.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ExchangeRateAPI {
    public Map<String, Integer> getExchangeRates () {
        RestTemplate restTemplate = new RestTemplate();
        String exchangeRateUrl = "https://open.er-api.com/v6/latest/USD";

        ResponseEntity<ERResponse> response = restTemplate.getForEntity(exchangeRateUrl, ERResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().getRates();
        }

        return null;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class ERResponse {
    private Map<String, Integer> rates;

    public Map<String, Integer> getRates() {
        return rates;
    }

    public void setRates(Map<String, Integer> rates) {
        this.rates = rates;
    }
}