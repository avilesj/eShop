package com.javiles.eshop.minio.services;

import com.javiles.eshop.minio.repositories.MinioRepository;
import com.javiles.eshop.storage.ProductImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class MinioStorageService implements ProductImageStorageService
{
    @Autowired
    private MinioRepository minioRepository;

    @Override
    public String storeFile(MultipartFile file)
    {
        String fileUrl = "";

        try
        {
            minioRepository.storeFileInBucket(file.getInputStream(), file.getSize(), this.generateFileName(file), file.getContentType());
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return fileUrl;

    }

    private String generateFileName(MultipartFile file)
    {
        String filename = file.getName();
        String size = String.valueOf(file.getSize());
        String time = String.valueOf(System.currentTimeMillis());

        return size + "_" + time + "_" + filename;
    }

}
