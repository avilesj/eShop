package com.javiles.eshop.storage;

import com.javiles.eshop.minio.MinioProperties;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class MinioStorageService implements ProductImageStorageService
{
    @Autowired
    MinioProperties minioProperties;

    @Override
    public String storeFile(MultipartFile file, String filename)
    {
        String productImageUrl = "";

        try
        {
            // Create a minioClient with the Minio Server name, Port, Access key and Secret key.
            MinioClient minioClient = new MinioClient(minioProperties.getUrl(), minioProperties.getAccessKey(), minioProperties.getSecretKey());

            // Check if the bucket already exists.
            boolean isExist = minioClient.bucketExists("eshop");
            if (isExist)
            {
                System.out.println("Bucket already exists.");
            } else
            {
                // Make a new bucket called asiatrip to hold a zip file of photos.
                minioClient.makeBucket("eshop");
            }

            // Upload the zip file to the bucket with putObject
            //minioClient.putObject("asiatrip","asiaphotos.zip", "/home/user/Photos/asiaphotos.zip");
            minioClient.putObject("eshop", "demonios", file.getInputStream(), file.getSize(), "image/jpg");
            productImageUrl = minioProperties.getUrl() + "/eshop/demonios";

            System.out.println("Successful upload to Minio");
        } catch (Exception e)
        {
            System.out.println("Error occurred: " + e);
        }

        return productImageUrl;
    }

}
