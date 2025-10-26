package com.bituan.country_currency_and_exchange_api.controller;

import com.bituan.country_currency_and_exchange_api.entity.CountryEntity;
import com.bituan.country_currency_and_exchange_api.exception.HttpException;
import com.bituan.country_currency_and_exchange_api.model.CountryModel;
import com.bituan.country_currency_and_exchange_api.service.CountryService;
import com.bituan.country_currency_and_exchange_api.service.ExchangeRateAPIService;
import com.bituan.country_currency_and_exchange_api.service.RestCountriesApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
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

    @PostMapping("/countries/refresh")
    public ResponseEntity<String> refreshCountries() throws HttpException {
        List<CountryModel> countries = restCountriesApiService.getCountries();
        Map<String, Double> exchangeRate = exchangeRateAPIService.getExchangeRates();

        List<CountryEntity> newCountries = new ArrayList<>();

        for (CountryModel country : countries) {
            CountryEntity countryEntity = new CountryEntity();

            //if country exists, set id so DB updates instead of inserts
            if (countryService.countryExists(country.getName().toLowerCase())) {
                countryEntity.setId(countryService.getCountryByName(country.getName()).getId());
            }

            countryEntity.setName(country.getName().toLowerCase());
            countryEntity.setCapital(country.getCapital());
            countryEntity.setRegion(country.getRegion());
            countryEntity.setPopulation(country.getPopulation());
            countryEntity.setFlagUrl(country.getFlagUrl());

            if (country.getCurrencies() == null) {
                countryEntity.setCurrencyCode(null);
                countryEntity.setExchangeRate(null);
                countryEntity.setEstimatedGdp((double) 0);
                countryEntity.setLastRefreshedAt(Instant.now());

                newCountries.add(countryEntity);
                continue;
            }

            String currencyCode = country.getCurrencies().get(0).get("code");
            countryEntity.setCurrencyCode(currencyCode);

            if (!exchangeRate.containsKey(currencyCode)) {
                countryEntity.setExchangeRate(null);
                countryEntity.setEstimatedGdp(null);
                countryEntity.setLastRefreshedAt(Instant.now());

                newCountries.add(countryEntity);
                continue;
            }

            // convert exchange rate to 2dp
            Double twoDpExchangeRate = Double.valueOf("%.2f".formatted(exchangeRate.get(currencyCode)));
            countryEntity.setExchangeRate(twoDpExchangeRate);

            Double randomNum = new Random().nextDouble(2000-1000+1) + 1000;
            Double estGdp = countryEntity.getPopulation() * randomNum / countryEntity.getExchangeRate();

            //convert estimated Gdp to 2dp
            Double oneDpEstGdp = Double.valueOf("%.1f".formatted(estGdp));
            countryEntity.setEstimatedGdp(oneDpEstGdp);

            countryEntity.setLastRefreshedAt(Instant.now());

            newCountries.add(countryEntity);
        }

        countryService.addCountries(newCountries);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/countries/{name}")
    public ResponseEntity<String> deleteCountry (@PathVariable String name) throws HttpException {

        if (!countryService.countryExists(name.toLowerCase())) {
            throw new HttpException(HttpStatus.NOT_FOUND, "Country not found", null);
        }

        countryService.deleteCountry(name.toLowerCase());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
