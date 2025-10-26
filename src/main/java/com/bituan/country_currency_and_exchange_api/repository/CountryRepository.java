package com.bituan.country_currency_and_exchange_api.repository;

import com.bituan.country_currency_and_exchange_api.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
    CountryEntity findByName (String name);
    void deleteByName (String name);
    boolean existsByName (String name);
    CountryEntity findFirstBy();
    List<CountryEntity> findAllOrderByGdpDesc();
    List<CountryEntity> findAllOrderByGdpAsc();
}
