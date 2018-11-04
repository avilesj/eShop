package com.javiles.eshop.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductImageStorageService
{
    void storeFile(MultipartFile file, String filename) throws IOException;
}
