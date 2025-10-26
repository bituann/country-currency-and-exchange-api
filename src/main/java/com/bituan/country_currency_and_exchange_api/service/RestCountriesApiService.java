package com.bituan.country_currency_and_exchange_api.service;

import com.bituan.country_currency_and_exchange_api.exception.HttpException;
import com.bituan.country_currency_and_exchange_api.model.CountryModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestCountriesApiService {
    public List<CountryModel> getCountries () throws HttpException {
        RestTemplate restTemplate = new RestTemplate();
        String restCountriesUrl = "https://restcountries.com/v2/all?fields=name,capital,region,population,flag,currencies";

        try {
            ResponseEntity<List<CountryModel>> response = restTemplate.exchange(restCountriesUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<CountryModel>>() {});

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (Exception e) {
            throw new HttpException(HttpStatus.valueOf(503), "External data source unavailable", "Could not fetch data from restcountries");
        }

        throw new HttpException(HttpStatus.valueOf(503), "External data source unavailable", "Could not fetch data from restcountries");
    }
}
