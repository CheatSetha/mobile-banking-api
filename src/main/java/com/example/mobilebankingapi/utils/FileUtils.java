package com.example.mobilebankingapi.utils;

import com.example.mobilebankingapi.api.file.FileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

@Component
public class FileUtils {
    @Value("${file.base_url}")
    private String fileBaseUrl;
    @Value("${file.server_path}")
    private String fileServerPath;
    @Value("${file.base_download_url}")
    private String fileBaseDownloadUrl;
    public FileDto upload(MultipartFile file) {
        int lastDotIndex = file.getOriginalFilename().lastIndexOf(".");
//        last dot index +1 to get extension and remove dot
//        below are follow our dto class
        String extension = file.getOriginalFilename().substring(lastDotIndex+1);
        long size = file.getSize();
        String name = String.format("%s.%s", UUID.randomUUID(),extension);
        String url = String.format("%s%s",fileBaseUrl,name);
        String downloadUrl = String.format("%s%s",fileBaseDownloadUrl,name);
        //in order to upload we use path
        Path path = Paths.get(fileServerPath+ name);
        try {
            Files.copy(file.getInputStream(),path);
            return FileDto.builder().name(name).extension(extension).size(size).url(url).donwloadUrl(downloadUrl).build();
        } catch (IOException e) {

            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"file upload error! buy me coffee if you want to fix this :(");
        }

    }
}
