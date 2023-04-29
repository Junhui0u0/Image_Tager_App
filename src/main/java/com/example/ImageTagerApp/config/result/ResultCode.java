package com.example.ImageTagerApp.config.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    // Image
    REGISTER_IMAGE_SUCCESS(201, "I001", "갤러리에 있는 캡쳐사진을 저장했습니다."),
    GET_IMAGE_SUCCESS(200, "I002", "사진을 조회했습니다."),
    REGISTER_IMAGE_TAG_SUCCESS(201, "I003", "사진에 태그를 추가했습니다."),
    DELETE_IMAGE_TAG_SUCCESS(200, "I004", "해당 태그를 삭제했습니다."),
    GET_IMAGE_BY_TAG_SUCCESS(200, "I005", "검색한 태그에 해당하는 사진 목록을 조회했습니다."),


    //Tag
    GET_TAG_SUCCESS(200, "T001", "모든 태그 리스트를 조회했습니다."),
    GET_BOOK_MARK_TAG_SUCCESS(200, "T002", "즐겨찾기한 태그 리스트를 조회했습니다."),
    UPDATE_BOOK_MARK_TAG_TO_TRUE_SUCCESS(201, "T003", "즐겨찾기에 새로운 태그를 저장했습니다."),
    UPDATE_BOOK_MARK_TAG_TO_FALSE_SUCCESS(200, "T004", "즐겨찾기에서 해당 태그를 삭제했습니다.");

    private final int status;
    private final String code;
    private final String message;
}