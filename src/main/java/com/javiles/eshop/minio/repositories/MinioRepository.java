package com.javiles.eshop.minio.repositories;

import com.javiles.eshop.minio.MinioProperties;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

public class MinioRepository
{
    @Autowired
    private MinioProperties minioProperties;

    public void storeFileInBucket(InputStream file, long fileSize, String filename, String contentType)
    {

        try
        {
            // Create a minioClient with the Minio Server name, Port, Access key and Secret key.
            MinioClient minioClient = new MinioClient(minioProperties.getUrl(), minioProperties.getAccessKey(), minioProperties.getSecretKey());

            // Check if the bucket already exists.
            boolean isExist = minioClient.bucketExists(minioProperties.getBucket());
            if (isExist)
            {
                System.out.println("Bucket already exists.");
            } else
            {
                // Make a new bucket
                minioClient.makeBucket(minioProperties.getBucket());
            }


            minioClient.putObject(minioProperties.getBucket(), filename, file, fileSize, contentType);

            System.out.println("Successful upload to Minio");
        } catch (Exception e)
        {
            System.out.println("Error occurred: " + e);
        }

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

    public String getFileUrlFromBucket(String filename)
    {
        String rootUrl = minioProperties.getUrl();
        String bucket = minioProperties.getBucket();
        return rootUrl + "/" + bucket + "/" + filename;
    }

}
