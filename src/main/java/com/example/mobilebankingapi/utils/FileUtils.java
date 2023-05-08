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
//        below are follow our dto constructor
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
    public class FileDownloadUtil {
        private Path foundFile;

        public Resource getFileAsResource(String fileCode) throws IOException {
            Path dirPath = Paths.get("Files-Upload");

            Files.list(dirPath).forEach(file -> {
                if (file.getFileName().toString().startsWith(fileCode)) {
                    foundFile = file;
                    return;
                }
            });

            if (foundFile != null) {
                return new UrlResource(foundFile.toUri());
            }

            return null;
        }
    }
//    public SearchFileDto isFileNameExist(String fileName) {
//        File file = new File(fileServerPath);
//        if (Objects.requireNonNull(file.list()).length > 0) {
//            List<File> fileList = new ArrayList<>(List.of(Objects.requireNonNull(file.listFiles())));
//            for (File f : fileList) {
//                if (f.getName().equals(fileName)) {
//                    return new SearchFileDto(f, true);
//                }
//            }
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND,
//                    String.format("file with name %s search not found", fileName)
//            );
//        }
//        throw new ResponseStatusException(
//                HttpStatus.NOT_FOUND,
//                "No file in server"
//        );
//    }
//
//    public String getExtensionFile(File file) {
//        int lastDotIndex = file.getName().lastIndexOf(".");
//        return file.getName().substring(lastDotIndex + 1);
//    }
//    public byte[] getImageData(String filename)  {
//        Path imagePath = Paths.get(fileServerPath + filename);
//        try {
//            return Files.readAllBytes(imagePath);
//        } catch (IOException e) {
//            throw new ResponseStatusException(
//                    HttpStatus.INTERNAL_SERVER_ERROR,
//                    "reading file error might be an array of the required size cannot be allocated!!"
//            );
//        }
//    }
//    public String getExtensionStringFile(String file) {
//        int lastDotIndex = file.lastIndexOf(".");
//        return file.substring(lastDotIndex + 1);
//    }
//    public String getContentType(String extension){
//        return switch (extension) {
//            case "pdf" -> "application/pdf";
//            case "png" -> "image/png";
//            case "webp" -> "image/webp";
//            default -> "application/octet-stream";
//        };
//    }
}
