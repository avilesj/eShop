package com.javiles.eshop.services;

import com.javiles.eshop.models.Country;
import com.javiles.eshop.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CountryServiceImpl implements CountryService
{
    @Autowired
    private CountryRepository countryRepository;

    @Override
    public List<Country> getAllCountries()
    {
        return countryRepository.findAll();
    }

    @Override
    public Country getCountryByCode(String countryCode)
    {
        return countryRepository.findByCountryCode(countryCode);
    }
}
