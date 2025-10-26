package com.bituan.country_currency_and_exchange_api.controller;

import com.bituan.country_currency_and_exchange_api.entity.CountryEntity;
import com.bituan.country_currency_and_exchange_api.exception.HttpException;
import com.bituan.country_currency_and_exchange_api.model.CountryModel;
import com.bituan.country_currency_and_exchange_api.model.StatusResponseModel;
import com.bituan.country_currency_and_exchange_api.service.CountryService;
import com.bituan.country_currency_and_exchange_api.service.ExchangeRateAPIService;
import com.bituan.country_currency_and_exchange_api.service.RestCountriesApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        // define timestamp
        Instant timestamp = Instant.now();

        for (CountryModel country : countries) {
            CountryEntity countryEntity = new CountryEntity();

            //if country exists, set id so DB updates instead of inserts
            if (countryService.countryExists(country.getName())) {
                countryEntity.setId(countryService.getCountryByName(country.getName()).getId());
            }

            countryEntity.setName(country.getName());
            countryEntity.setCapital(country.getCapital());
            countryEntity.setRegion(country.getRegion());
            countryEntity.setPopulation(country.getPopulation());
            countryEntity.setFlagUrl(country.getFlag());
            countryEntity.setLastRefreshedAt(timestamp);

            if (country.getCurrencies() == null) {
                countryEntity.setCurrencyCode(null);
                countryEntity.setExchangeRate(null);
                countryEntity.setEstimatedGdp((double) 0);

                newCountries.add(countryEntity);
                continue;
            }

            String currencyCode = country.getCurrencies().get(0).get("code");
            countryEntity.setCurrencyCode(currencyCode);

            if (!exchangeRate.containsKey(currencyCode)) {
                countryEntity.setExchangeRate(null);
                countryEntity.setEstimatedGdp(null);

                newCountries.add(countryEntity);
                continue;
            }

            // convert exchange rate to 2dp
            Double twoDpExchangeRate = Double.valueOf("%.2f".formatted(exchangeRate.get(currencyCode)));
            countryEntity.setExchangeRate(twoDpExchangeRate);

            int min = 1000;
            int max = 2000;
            Double randomNum = new Random().nextDouble(max - min + 1) + min;
            Double estGdp = countryEntity.getPopulation() * randomNum / countryEntity.getExchangeRate();

            //convert estimated Gdp to 2dp
            Double oneDpEstGdp = Double.valueOf("%.1f".formatted(estGdp));
            countryEntity.setEstimatedGdp(oneDpEstGdp);

            newCountries.add(countryEntity);
        }

        countryService.addCountries(newCountries);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/countries/{name}")
    public ResponseEntity<CountryEntity> getCountry (@PathVariable String name) throws HttpException {
        if (!countryService.countryExists(name)) {
            throw new HttpException(HttpStatus.NOT_FOUND, "Country not found", null);
        }

        CountryEntity country = countryService.getCountryByName(name);
        return ResponseEntity.ok(country);
    }

    @GetMapping("/countries/status")
    public ResponseEntity<StatusResponseModel> getStatus () {
        StatusResponseModel response = new StatusResponseModel();

        response.setTotalCountries(countryService.countTotalRows());
        response.setLastRefreshedAt(countryService.findFirst().getLastRefreshedAt());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/countries/{name}")
    public ResponseEntity<String> deleteCountry (@PathVariable String name) throws HttpException {

        if (!countryService.countryExists(name)) {
            throw new HttpException(HttpStatus.NOT_FOUND, "Country not found", null);
        }

        countryService.deleteCountry(name);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
