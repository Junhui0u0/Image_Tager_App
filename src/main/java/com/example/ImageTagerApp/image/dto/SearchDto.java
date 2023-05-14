package com.example.ImageTagerApp.image.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SearchDto {
    private List<String> tagName;
}
