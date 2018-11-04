package com.javiles.eshop.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductImageStorageService
{
    String storeFile(MultipartFile file) throws IOException;
}
