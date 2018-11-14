package com.javiles.eshop.services;

import com.javiles.eshop.models.Country;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public interface CountryService
{
    List<Country> getAllCountries();

    Country getCountryByCode(String countryCode);
}
