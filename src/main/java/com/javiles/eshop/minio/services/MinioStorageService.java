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
    public String storeFile(MultipartFile file, String filename)
    {
        String newFileName = this.generateFileName(file, filename);
        try
        {
            minioRepository.storeFileInBucket(file.getInputStream(), file.getSize(), newFileName, file.getContentType());
            return newFileName;
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public void deleteFile(String filename)
    {
        minioRepository.deleteFileFromBucket(filename);
    }

    @Override
    public String getFileUrl(String filename)
    {
        return minioRepository.getFileUrlFromBucket(filename);
    }

    private String generateFileName(MultipartFile file, String filename)
    {
        int lastIndex = file.getName().lastIndexOf(".");
        String fileExtension = file.getName().substring(lastIndex);
        String size = String.valueOf(file.getSize());
        String time = String.valueOf(System.currentTimeMillis());

        return size + "_" + time + "_" + filename + fileExtension;
    }

}
