package com.bituan.country_currency_and_exchange_api.service;

import com.bituan.country_currency_and_exchange_api.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CountryService {

    private CountryRepository countryRepository;

    @Autowired
    public CountryService (CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

}
