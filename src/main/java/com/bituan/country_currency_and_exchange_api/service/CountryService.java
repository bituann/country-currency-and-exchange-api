package com.bituan.country_currency_and_exchange_api.service;

import com.bituan.country_currency_and_exchange_api.entity.CountryEntity;
import com.bituan.country_currency_and_exchange_api.model.CountryModel;
import com.bituan.country_currency_and_exchange_api.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryService (CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public boolean countryExists (String name) {
        return countryRepository.existsByName(name);
    }

    public void addCountry (CountryEntity country) {
        countryRepository.save(country);
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
