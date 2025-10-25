package com.bituan.country_currency_and_exchange_api.service;

import com.bituan.country_currency_and_exchange_api.model.CountryModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestCountriesApiService {
    public List<CountryModel> getCountries () {
        RestTemplate restTemplate = new RestTemplate();
        String restCountriesUrl = "https://restcountries.com/v2/all?fields=name,capital,region,population,flag,currencies";

        ResponseEntity<Response> response = restTemplate.getForEntity(restCountriesUrl, Response.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().getResponse();
        }

        return null;
    }
}

class Response {
    private List<CountryModel> response;

    public List<CountryModel> getResponse() {
        return response;
    }

    public void setResponse(List<CountryModel> response) {
        this.response = response;
    }
}
