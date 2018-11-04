package com.javiles.eshop.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileSystemStorageService implements ProductImageStorageService
{
    private final Path rootPath = Paths.get("C:\\eShop\\files\\products\\");

    @Override
    public String storeFile(MultipartFile file, String filename) throws IOException
    {
        try (InputStream inputStream = file.getInputStream())
        {
            Files.copy(inputStream, this.rootPath.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
        }

        return rootPath + filename;
    }
}
