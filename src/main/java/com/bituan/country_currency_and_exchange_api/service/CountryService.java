package com.bituan.country_currency_and_exchange_api.service;

import com.bituan.country_currency_and_exchange_api.entity.CountryEntity;
import com.bituan.country_currency_and_exchange_api.model.CountryModel;
import com.bituan.country_currency_and_exchange_api.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService (CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<CountryEntity> getAllCountries () {
        return countryRepository.findAll();
    }

    public CountryEntity getCountryByName (String name) {
        return countryRepository.findByName(name);
    }

    public void deleteCountry (String name) {
        countryRepository.deleteByName(name);
    }
}
