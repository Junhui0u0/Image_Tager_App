package com.example.ImageTagerApp.config.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    FILE_UPLOAD_ERROR(500,"FILE-ERR-500","FILE UPLOAD ERROR"),
    UPLOAD_FILE_NOTEXIST(500,"FILE-ERR-500","UPLOAD FILE NOT EXIST");

    final private int status;
    final private String errorCode;
    final private String message;
}
