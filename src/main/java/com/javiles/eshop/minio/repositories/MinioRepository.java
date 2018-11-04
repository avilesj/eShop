package com.javiles.eshop.minio.repositories;

import com.javiles.eshop.minio.MinioProperties;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

public class MinioRepository
{
    @Autowired
    private MinioProperties minioProperties;

    public String storeFileInBucket(InputStream file, long fileSize, String filename, String contentType)
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


            minioClient.putObject(minioProperties.getBucket(), filename, file, fileSize, contentType);
            productImageUrl = minioProperties.getUrl() + "/" + minioProperties.getBucket() + "/" + filename;

            System.out.println("Successful upload to Minio");
        } catch (Exception e)
        {
            System.out.println("Error occurred: " + e);
        }

        return productImageUrl;
    }

    public void deleteFileFromBucket(String filename)
    {
        try
        {
            // Create a minioClient with the Minio Server name, Port, Access key and Secret key.
            MinioClient minioClient = new MinioClient(minioProperties.getUrl(), minioProperties.getAccessKey(), minioProperties.getSecretKey());
            minioClient.removeObject(minioProperties.getBucket(), filename);
            System.out.println("Successfully removed from Minio");
        } catch (Exception e)
        {
            System.out.println("Error occurred: " + e);
        }
    }

}
