package com.bituan.country_currency_and_exchange_api.service;

import com.bituan.country_currency_and_exchange_api.model.CountryModel;
import com.bituan.country_currency_and_exchange_api.model.RcResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestCountriesApiService {
    public List<CountryModel> getCountries () {
        RestTemplate restTemplate = new RestTemplate();
        String restCountriesUrl = "https://restcountries.com/v2/all?fields=name,capital,region,population,flag,currencies";

        ResponseEntity<RcResponseModel> response = restTemplate.getForEntity(restCountriesUrl, RcResponseModel.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().getResponse();
        }

        return null;
    }
}
