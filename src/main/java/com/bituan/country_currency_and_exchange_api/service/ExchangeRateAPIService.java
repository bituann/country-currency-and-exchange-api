package com.bituan.country_currency_and_exchange_api.service;

import com.bituan.country_currency_and_exchange_api.model.ErResponseModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ExchangeRateAPIService {
    public Map<String, Integer> getExchangeRates () {
        RestTemplate restTemplate = new RestTemplate();
        String exchangeRateUrl = "https://open.er-api.com/v6/latest/USD";

        ResponseEntity<ErResponseModel> response = restTemplate.getForEntity(exchangeRateUrl, ErResponseModel.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().getRates();
        }

        return null;
    }
}