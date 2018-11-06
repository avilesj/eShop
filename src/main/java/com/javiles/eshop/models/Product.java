package com.javiles.eshop.models;

import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;

@Entity
public class Product
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false, precision = 10, scale = 2)
    @NumberFormat(pattern = "#00.00")
    private double price;
    private String imageFilename;

    @Transient
    private String imageFileUrl;

    public String getImageFileUrl()
    {
        return imageFileUrl;
    }

    public void setImageFileUrl(String imageFileUrl)
    {
        this.imageFileUrl = imageFileUrl;
    }

    public String getImageFilename()
    {
        return imageFilename;
    }

    public void setImageFilename(String imageFilename)
    {
        this.imageFilename = imageFilename;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }


}
