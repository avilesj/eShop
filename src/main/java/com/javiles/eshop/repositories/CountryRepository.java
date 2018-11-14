package com.javiles.eshop.repositories;

import com.javiles.eshop.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long>
{
    Country findByCountryCode(String countryCode);
}
