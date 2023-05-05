package com.example.mobilebankingapi.api.file;

import lombok.Builder;

@Builder
public record FileDto(String name ,
                      String url,
                      String extension,
                      String donwloadUrl,
                      long size) {

}
