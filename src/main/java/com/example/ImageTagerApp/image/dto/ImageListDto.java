package com.example.ImageTagerApp.image.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@Getter
public class ImageListDto {
    private Long imageId;
    private String imageUrl;
}
