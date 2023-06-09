package com.example.ImageTagerApp.image.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class ImageListDto {
    private Long imageId;
    private String fileName;
    private List<String> tags;
}
