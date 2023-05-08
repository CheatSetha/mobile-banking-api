package com.example.mobilebankingapi.api.file;

import java.io.File;

public record SearchFileDto(
        File file,
        boolean status)
{
}
