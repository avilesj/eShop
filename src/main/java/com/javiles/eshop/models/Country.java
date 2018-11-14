package com.javiles.eshop.models;

import javax.persistence.*;

@Entity
@Table(name = "Country")
public class Country
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String countryCode;
    private String countryName;

    public String getCountryCode()
    {
        return countryCode;
    }

    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }

    public String getCountryName()
    {
        return countryName;
    }

    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
}
