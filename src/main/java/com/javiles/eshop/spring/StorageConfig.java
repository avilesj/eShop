package com.javiles.eshop.spring;

import com.javiles.eshop.storage.FileSystemStorageService;
import com.javiles.eshop.storage.ProductImageStorageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig
{
    @Bean
    public ProductImageStorageService productImageStorageService()
    {
        return new FileSystemStorageService();
    }
}
