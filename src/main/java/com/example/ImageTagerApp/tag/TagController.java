package com.example.ImageTagerApp.tag;

import com.example.ImageTagerApp.config.result.ResultCode;
import com.example.ImageTagerApp.config.result.ResultResponse;
import com.example.ImageTagerApp.tag.dto.UserDeviceTokenDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Log4j2
public class TagController {
    private  final TagService tagService;


    //모든 태그 조회
    @ApiOperation(value="모든 태그 조회")
    @GetMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> getTags(@RequestBody UserDeviceTokenDto userDeviceTokenDto){
        final List<String> data= tagService.getTags(userDeviceTokenDto.getUserDeviceToken());
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_TAG_SUCCESS, data));
    }

    //즐겨찾기한 태그 조회
    @ApiOperation(value="즐겨찾기한 태그 조회")
    @GetMapping(value = "/book-mark-tags", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> getBookMarkTags(@RequestBody UserDeviceTokenDto userDeviceTokenDto){
        final List<String> data= tagService.getBookMarkTags(userDeviceTokenDto.getUserDeviceToken());
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_BOOK_MARK_TAG_SUCCESS, data));
    }

    //즐겨찾기에 새로운 태그 저장
    @ApiOperation(value="즐겨찾기에 새로운 태그 저장")
    @PostMapping(value = "/book-mark-tags/{tag_name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> registerBookMarkTag(@PathVariable("tag_name") String tagName, @RequestBody UserDeviceTokenDto userDeviceTokenDto){
        tagService.registerBookMarkTag(tagName, userDeviceTokenDto.getUserDeviceToken());
        return ResponseEntity.ok(ResultResponse.of(ResultCode.REGISTER_BOOK_MARK_TAG_SUCCESS));
    }

    //즐겨찾기에서 태그 삭제
    @ApiOperation(value="즐겨찾기에서 태그 삭제")
    @DeleteMapping(value = "/book-mark-tags/{tag_name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse> deleteBookMarkTag(@PathVariable("tag_name") String tagName, @RequestBody UserDeviceTokenDto userDeviceTokenDto){
        tagService.deleteBookMarkTag(tagName, userDeviceTokenDto.getUserDeviceToken());
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_BOOK_MARK_TAG_SUCCESS));
    }
}
