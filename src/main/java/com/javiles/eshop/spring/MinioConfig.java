package com.javiles.eshop.spring;

import com.javiles.eshop.minio.MinioProperties;
import com.javiles.eshop.minio.repositories.MinioRepository;
import com.javiles.eshop.minio.services.MinioStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:minio.properties")
public class MinioConfig
{
    @Autowired
    private Environment environment;

    @Bean
    public MinioProperties minioPropertiesProperties()
    {
        MinioProperties minioProperties = new MinioProperties();
        minioProperties.setSecretKey(environment.getProperty("minio.secretkey"));
        minioProperties.setAccessKey(environment.getProperty("minio.accesskey"));
        minioProperties.setUrl(environment.getProperty("minio.url"));
        minioProperties.setBucket(environment.getProperty("minio.bucket").toLowerCase());
        return minioProperties;
    }

    @Bean
    public MinioStorageService minioStorageService()
    {
        return new MinioStorageService();
    }

    @Bean
    public MinioRepository minioRepository()
    {
        return new MinioRepository();
    }

}
