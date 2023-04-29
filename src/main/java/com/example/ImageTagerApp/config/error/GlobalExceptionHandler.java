package com.example.ImageTagerApp.config.error;


import com.example.ImageTagerApp.config.S3.FileUploadException;
import com.example.ImageTagerApp.config.S3.UploadFileNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UploadFileNotExistException.class)
    public ResponseEntity<ErrorResponse> handleUploadFileNotExistException(UploadFileNotExistException ex){
        log.error("handleUploadFileNotExistException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorResponse> handleFileUploadException(FileUploadException ex){
        log.error("handleFileUploadException",ex);
        final ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

}
