package com.bituan.country_currency_and_exchange_api.controller;

import com.bituan.country_currency_and_exchange_api.service.CountryService;
import com.bituan.country_currency_and_exchange_api.service.ExchangeRateAPIService;
import com.bituan.country_currency_and_exchange_api.service.RestCountriesApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CountryController {

    private final CountryService countryService;
    private final ExchangeRateAPIService exchangeRateAPIService;
    private final RestCountriesApiService restCountriesApiService;

    @Autowired
    public CountryController (CountryService countryService, ExchangeRateAPIService exchangeRateAPIService,
                       RestCountriesApiService restCountriesApiService) {
        this.countryService = countryService;
        this.exchangeRateAPIService = exchangeRateAPIService;
        this.restCountriesApiService = restCountriesApiService;
    }


}
