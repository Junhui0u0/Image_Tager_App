package com.example.ImageTagerApp.image.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SearchDto {
    private String userDeviceToken;
    private List<String> tagName;
}
