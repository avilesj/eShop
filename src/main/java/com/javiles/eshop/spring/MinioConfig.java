package com.javiles.eshop.spring;

import com.javiles.eshop.minio.MinioProperties;
import com.javiles.eshop.storage.MinioStorageService;
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
        return minioProperties;
    }

    @Bean
    public MinioStorageService minioStorageService()
    {
        return new MinioStorageService();
    }

}
