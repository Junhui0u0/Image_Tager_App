package com.example.ImageTagerApp.image.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import org.springframework.data.util.Pair;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class ImageDto {
    private Long imageId;
    private String fileName;
    private List<Pair<Long,String>> tags;
}