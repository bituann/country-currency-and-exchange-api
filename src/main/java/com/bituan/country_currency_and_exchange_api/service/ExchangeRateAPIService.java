package com.bituan.country_currency_and_exchange_api.service;

import com.bituan.country_currency_and_exchange_api.exception.HttpException;
import com.bituan.country_currency_and_exchange_api.model.ErResponseModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ExchangeRateAPIService {
    public Map<String, Double> getExchangeRates () throws HttpException {
        RestTemplate restTemplate = new RestTemplate();
        String exchangeRateUrl = "https://open.er-api.com/v6/latest/USD";

        try {
            ResponseEntity<ErResponseModel> response = restTemplate.getForEntity(exchangeRateUrl, ErResponseModel.class);


            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody().getRates();
            }
        } catch (Exception e) {
            throw new HttpException(HttpStatus.valueOf(503), "External data source unavailable", "Could not fetch data from open-er-api");
        }

        throw new HttpException(HttpStatus.valueOf(503), "External data source unavailable", "Could not fetch data from open-er-api");
    }
}