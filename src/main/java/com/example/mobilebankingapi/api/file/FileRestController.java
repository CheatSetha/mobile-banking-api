package com.example.mobilebankingapi.api.file;

import com.example.mobilebankingapi.base.BaseRest;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
public class FileRestController {
    private final FileService fileService;

    @PostMapping
    public BaseRest<?> uploadSingleFile(@RequestPart("file") MultipartFile file) {
//check file name when upload
        System.out.println(file.getOriginalFilename());

        FileDto fileDto = fileService.uploadSingleFile(file);
        return BaseRest.builder().status(true).code(HttpStatus.OK.value()).message("file have been uploaded successfully").timestamp(LocalDateTime.now()).data(fileDto).build();
    }

    @PostMapping("/multiple")
    public BaseRest<?> uploadMultipleFiles(@RequestPart("files") List<MultipartFile> files) {
        List<FileDto> fileDtos = fileService.uploadMultipleFiles(files);
        return BaseRest.builder().status(true).code(HttpStatus.OK.value()).message("files have been uploaded successfully").timestamp(LocalDateTime.now()).data(fileDtos).build();
    }
//    delete file if exist

    @DeleteMapping("/{fileName}")
    public BaseRest<?> deleteFile(@PathVariable("fileName") String fileName) {
        boolean isDeleted = fileService.deleteFile(fileName);
        return BaseRest.builder().status(true).code(HttpStatus.OK.value()).message("file have been deleted successfully").timestamp(LocalDateTime.now()).data(isDeleted).build();
    }
    @DeleteMapping("/delete-all")
    public BaseRest<?> deleteAllFiles() {
        boolean isDeleted = fileService.deleteAllFile();
        return BaseRest.builder().status(true).code(HttpStatus.OK.value()).message("files have been deleted successfully").timestamp(LocalDateTime.now()).data(isDeleted).build();
    }


    @GetMapping
    public BaseRest<?> getAllFiles(){
        List<FileDto> filesDto = fileService.getAllFiles();
        return BaseRest.builder().status(true).code(HttpStatus.OK.value()).message("files have been fetched successfully").timestamp(LocalDateTime.now()).data(filesDto).build();
    }

    @GetMapping("/{name}")
    public BaseRest<?> getFileByName(@PathVariable("name") String name){
        FileDto fileDto = fileService.getFileByName(name);
        return BaseRest.builder().status(true).code(HttpStatus.OK.value()).message("file have been fetched successfully").timestamp(LocalDateTime.now()).data(fileDto).build();
    }

    @GetMapping("/search")
    public BaseRest<?> searchFileByName(@RequestParam("name") String name){
        List<FileDto> filesDto = fileService.searchFileByName(name);
        return BaseRest.builder().status(true).code(HttpStatus.OK.value()).message("files have been fetched successfully").timestamp(LocalDateTime.now()).data(filesDto).build();
    }
}
