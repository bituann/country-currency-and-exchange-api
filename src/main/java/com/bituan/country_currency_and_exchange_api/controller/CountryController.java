package com.bituan.country_currency_and_exchange_api.controller;

import com.bituan.country_currency_and_exchange_api.entity.CountryEntity;
import com.bituan.country_currency_and_exchange_api.exception.HttpException;
import com.bituan.country_currency_and_exchange_api.model.CountryModel;
import com.bituan.country_currency_and_exchange_api.model.FilterParamModel;
import com.bituan.country_currency_and_exchange_api.model.StatusResponseModel;
import com.bituan.country_currency_and_exchange_api.service.CountryService;
import com.bituan.country_currency_and_exchange_api.service.ExchangeRateAPIService;
import com.bituan.country_currency_and_exchange_api.service.ImageGenerationService;
import com.bituan.country_currency_and_exchange_api.service.RestCountriesApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class CountryController {

    private final CountryService countryService;
    private final ExchangeRateAPIService exchangeRateAPIService;
    private final RestCountriesApiService restCountriesApiService;
    private final ImageGenerationService imageGenerationService;

    @Autowired
    public CountryController (CountryService countryService, ExchangeRateAPIService exchangeRateAPIService,
                              RestCountriesApiService restCountriesApiService, ImageGenerationService imageGenerationService) {
        this.countryService = countryService;
        this.exchangeRateAPIService = exchangeRateAPIService;
        this.restCountriesApiService = restCountriesApiService;
        this.imageGenerationService = imageGenerationService;
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

        countryService.deleteAllCountries();
        countryService.addCountries(newCountries);

        imageGenerationService.generateImage("Hello", "summary");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/countries/image")
    public ResponseEntity<?> getImage () throws HttpException {
        Path path = Paths.get("summary.png");

        if (Files.notExists(path)) {
            throw new HttpException(HttpStatus.NOT_FOUND, "Summary image not found", null);
        }

        try {
            Resource imageResource = new UrlResource(path.toUri());
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageResource);
        } catch (MalformedURLException e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", null);
        }
    }

    @GetMapping("/countries/{name}")
    public ResponseEntity<CountryEntity> getCountry (@PathVariable String name) throws HttpException {
        if (!countryService.countryExists(name)) {
            throw new HttpException(HttpStatus.NOT_FOUND, "Country not found", null);
        }

        CountryEntity country = countryService.getCountryByName(name);
        return ResponseEntity.ok(country);
    }

    @GetMapping("/countries")
    public ResponseEntity<List<CountryEntity>> getCountries (@RequestParam("sort_by") String sortBy, FilterParamModel filters) throws HttpException {
        List<CountryEntity> countries = countryService.getAllCountries();

        if (filters == null) {
            return ResponseEntity.ok(countries);
        }

        if (filters.getRegion() != null) {
            countries = countries.stream()
                    .filter(country -> country.getRegion().equals(filters.getRegion()))
                    .collect(Collectors.toList());
        }

        if (filters.getCurrency() != null) {
            countries = countries.stream()
                    .filter(country -> country.getCurrencyCode() != null && country.getCurrencyCode().equals(filters.getCurrency()))
                    .collect(Collectors.toList());
        }

        if (filters.getGdp() != null) {
            countries = countries.stream()
                    .filter(country -> country.getEstimatedGdp().equals(filters.getGdp()))
                    .collect(Collectors.toList());
        }

        if (sortBy != null) {
            List<CountryEntity> countriesSorted = countryService.getSortedList(sortBy);

            Set<CountryEntity> countriesSet = new HashSet<>(countries);
            countriesSorted = countriesSorted.stream().filter(countriesSet::contains).toList();
            countries = countriesSorted;
        }

        return ResponseEntity.ok(countries);
    }

    @GetMapping("/status")
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
