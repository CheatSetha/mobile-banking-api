package com.example.mobilebankingapi.api.file;

import com.example.mobilebankingapi.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FileServiceImple implements FileService {
    @Value("${file.base_url}")
    private String fileBaseUrl;
    @Value("${file.server_path}")
    private String fileServerPath;
    @Value("${file.base_download_url}")
    private String fileBaseDownloadUrl;

    private FileUtils fileUtils;

    @Autowired
    public void setFileUtils(FileUtils fileUtils) {
        this.fileUtils = fileUtils;
    }

    @Override
    public FileDto uploadSingleFile(MultipartFile file) {
        return fileUtils.upload(file);


    }

    @Override
    public List<FileDto> uploadMultipleFiles(List<MultipartFile> multipleFiles) {
        List<FileDto> filesDto = new ArrayList<>();
        for (MultipartFile file : multipleFiles) {
            filesDto.add(fileUtils.upload(file));
        }
        return filesDto;
    }

    @Override
    public boolean deleteFile(String fileName) {
        Path path = Paths.get(fileServerPath + fileName);
        try {
            Files.delete(path);
            return true;
        } catch (IOException e) {
            log.error("file delete error: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "file delete error");
        }
    }

    //    delete all files
    @Override
    public boolean deleteAllFile() {
        try {
            Files.walk(Paths.get(fileServerPath)).filter(Files::isRegularFile).forEach(file -> {
                try {
                    Files.delete(file);
                } catch (IOException e) {
                    log.error("file delete error: {}", e.getMessage());
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "file delete error");
                }
            });
            return true;
        } catch (IOException e) {
            log.error("file delete error: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "file delete error");
        }
    }

    @Override
    public Resource download(String fileName) {
        try {
            Path file = Paths.get(fileServerPath + fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            log.error("file load error: {}", e.getMessage());
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    @Override
    public List<FileDto> getAllFiles() {
        List<FileDto> filesDto = new ArrayList<>();
        try {
            Files.walk(Paths.get(fileServerPath)).filter(Files::isRegularFile).forEach(file -> {
                String name = file.getFileName().toString();
                String extension = name.substring(name.lastIndexOf(".") + 1);
                //get size of file
                long size = file.toFile().length();
                String url = String.format("%s%s", fileBaseUrl, name);
                String downloadUrl = String.format("%s%s", fileBaseDownloadUrl, name);
                filesDto.add(FileDto.builder().name(name).extension(extension).size(size).url(url).donwloadUrl(downloadUrl).build());
            });
        } catch (IOException e) {
            log.error("file get all error: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "file get all error");
        }
        return filesDto;
    }

    @Override
    public FileDto getFileByName(String name) {
        Path path = Paths.get(fileServerPath + name);
        try {
            String extension = name.substring(name.lastIndexOf(".") + 1);
            //get size of file
            long size = Files.size(path);
            String url = String.format("%s%s", fileBaseUrl, name);
            String donwloadUrl = String.format("%s%s", fileBaseDownloadUrl, name);
            return FileDto.builder().name(name).extension(extension).size(size).url(url).donwloadUrl(donwloadUrl).build();
        } catch (IOException e) {
            log.error("file get by name error: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "file not found ");
        }
    }

    @Override
    public List<FileDto> searchFileByName(String name) {
        List<FileDto> filesDto = new ArrayList<>();

        try {
            Files.walk(Paths.get(fileServerPath)).filter(Files::isRegularFile).forEach(file -> {
                String fileName = file.getFileName().toString();
                if (fileName.contains(name)) {
                    String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
                    //get size of file
                    long size = file.toFile().length();
                    String url = String.format("%s%s", fileBaseUrl, fileName);
                    String downloadUrl = String.format("%s%s", fileBaseDownloadUrl, fileName);
                    filesDto.add(FileDto.builder().name(fileName).extension(extension).size(size).url(url).donwloadUrl(downloadUrl).build());
                }
            });
        } catch (IOException e) {
            log.error("file search error: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "file not found");
        }
        return filesDto;

    }

}

