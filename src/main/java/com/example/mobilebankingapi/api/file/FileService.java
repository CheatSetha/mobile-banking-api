package com.example.mobilebankingapi.api.file;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    /**
     * upload single file use multipart file
     * @param file request form data from client
     * @return FileDto
     */
    FileDto uploadSingleFile(MultipartFile file);

    /**
     * upload multiple file use multipart file
     * @param files request form data from client
     * @return FileDto
     */
    List<FileDto> uploadMultipleFiles(List<MultipartFile> files);

//    delete file if exist

    /**
     * delete file if exist
     * @param fileName file name
     */
    boolean deleteFile(String fileName);

    /**
     * get all files and return as list
     * @return
     */
    List<FileDto> getAllFiles();

//    get file by name
    FileDto getFileByName(String name);

//    search file by name
    List<FileDto> searchFileByName(String name);


//    delete all file
    boolean deleteAllFile();
//    download file via uri
    Resource load(String fileName);
}
