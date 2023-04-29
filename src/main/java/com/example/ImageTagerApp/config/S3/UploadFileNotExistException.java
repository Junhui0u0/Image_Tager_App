package com.example.ImageTagerApp.config.S3;

import com.example.ImageTagerApp.config.error.ErrorCode;
import lombok.Getter;

@Getter
public class UploadFileNotExistException extends RuntimeException{
    private final ErrorCode errorCode;

    public UploadFileNotExistException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}