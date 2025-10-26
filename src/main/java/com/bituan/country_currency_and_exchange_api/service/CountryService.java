package com.bituan.country_currency_and_exchange_api.service;

import com.bituan.country_currency_and_exchange_api.entity.CountryEntity;
import com.bituan.country_currency_and_exchange_api.exception.HttpException;
import com.bituan.country_currency_and_exchange_api.repository.CountryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public void addCountries (List<CountryEntity> countries) {
        countryRepository.saveAll(countries);
    }

    public List<CountryEntity> getAllCountries () {
        return countryRepository.findAll();
    }

    public CountryEntity getCountryByName (String name) {
        return countryRepository.findByName(name);
    }

    @Transactional
    public void deleteCountry (String name) {
        countryRepository.deleteByName(name);
    }

    public long countTotalRows () {
        return countryRepository.count();
    }

    public CountryEntity findFirst () {
        return countryRepository.findFirstBy();
    }

    public List<CountryEntity> getSortedList(String sortBy) throws HttpException {
        return switch (sortBy) {
            case "gdp_desc" -> countryRepository.findAllByOrderByEstimatedGdpDesc();
            case "gdp_asc" -> countryRepository.findAllByOrderByEstimatedGdpAsc();
            default -> throw new HttpException(HttpStatus.BAD_REQUEST, "Validation failed", null);
        };

    }
}
