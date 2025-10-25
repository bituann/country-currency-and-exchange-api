package com.bituan.country_currency_and_exchange_api.repository;

import com.bituan.country_currency_and_exchange_api.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
    List<CountryEntity> findByName (String name);
}
