package com.example.ImageTagerApp.image;

import com.example.ImageTagerApp.config.result.ResultCode;
import com.example.ImageTagerApp.config.result.ResultResponse;
import com.example.ImageTagerApp.image.dto.ImageDto;
import com.example.ImageTagerApp.image.dto.ImageListDto;
import com.example.ImageTagerApp.image.dto.SearchDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Log4j2
public class ImageController {
    private final ImageService imageService;

    //갤러리에 있는 캡쳐사진 저장
    @ApiOperation(value="갤러리에 있는 캡쳐사진 저장")
    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultResponse> registerImages(@RequestPart("images") List<MultipartFile> images, @RequestPart("userDeviceToken") String userDeviceToken){
        imageService.registerImages(images, userDeviceToken);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.REGISTER_IMAGE_SUCCESS));
    }

    //사진 조회
    @ApiOperation(value="사진 조회")
    @GetMapping(value = "/images/{image_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> getImage(@PathVariable("image_id") Long imageId){
        ImageDto data= imageService.getImage(imageId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_IMAGE_SUCCESS, data));
    }

    //사진 태그 추가
    @ApiOperation(value="사진 태그 추가")
    @PostMapping(value = "/image/{image_id}/tag/{tag_name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> registerImageTag(@PathVariable("image_id") Long imageId, @PathVariable("tag_name") String tagName){
        imageService.registerImageTag(imageId, tagName);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.REGISTER_IMAGE_TAG_SUCCESS));
    }

    //사진 태그 삭제
    @ApiOperation(value="사진 태그 삭제")
    @DeleteMapping(value = "/image/tag/{tag_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> deleteImageTag(@PathVariable("tag_id") Long tagId){
        imageService.deleteImageTag(tagId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_IMAGE_TAG_SUCCESS));
    }

    //검색한 태그에 해당하는 사진 조회
    @ApiOperation(value="검색한 태그에 해당하는 사진 조회")
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> getImagesByTag(@RequestBody SearchDto searchDto){
        List<ImageListDto> data= imageService.getImagesByTag(searchDto);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_IMAGE_BY_TAG_SUCCESS, data));
    }
}
