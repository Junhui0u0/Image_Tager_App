package com.example.ImageTagerApp.image.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class ImageDto {
    private Long imageId;
    private String imageUrl;
    private List<String> tags;
}
