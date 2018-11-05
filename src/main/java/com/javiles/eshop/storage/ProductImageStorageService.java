package com.javiles.eshop.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductImageStorageService
{
    String storeFile(MultipartFile file, String filename) throws IOException;

    void deleteFile(String filename) throws Exception;

    String getFileUrl(String filename) throws Exception;
}
