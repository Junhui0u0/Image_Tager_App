package com.example.ImageTagerApp.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final  TagRepository tagRepository;


    //모든 태그 조회
    @Transactional(readOnly = true)
    List<String> getTags(final String userDeviceToken){
        final List<String> result= new ArrayList<>();
        final HashMap<String,Integer> duplicateChk= new HashMap<>();

        for(Tag tag: tagRepository.findAllByUserDeviceToken(userDeviceToken)){
            if(duplicateChk.containsKey(tag.getTagName())) continue;
            result.add(tag.getTagName());
            duplicateChk.put(tag.getTagName(),1);
        }

        return result;
    }


    //즐겨찾기한 태그 조회
    @Transactional(readOnly = true)
    List<String> getBookMarkTags(final String userDeviceToken){
        final List<String> result= new ArrayList<>();
        final HashMap<String,Integer> duplicateChk= new HashMap<>();

        for(Tag tag: tagRepository.findAllByUserDeviceToken(userDeviceToken)){
            if(!tag.isFlagBookMark()) continue;
            if(duplicateChk.containsKey(tag.getTagName())) continue;
            result.add(tag.getTagName());
            duplicateChk.put(tag.getTagName(),1);
        }

        return result;
    }


    //즐겨찾기에 새로운 태그 저장
    @Transactional
    void updateBookMarkTagOn(final String tagName, final String userDeviceToken){
        for(Tag tag: tagRepository.findAllByTagNameAndUserDeviceToken(tagName, userDeviceToken)){
            tag.updateFlagBookMark(true);
            tagRepository.save(tag);
        }
    }


    //즐겨찾기에서 태그 삭제
    @Transactional
    void updateBookMarkTagOff(final String tagName, final String userDeviceToken){
        for(Tag tag: tagRepository.findAllByTagNameAndUserDeviceToken(tagName, userDeviceToken)){
            tag.updateFlagBookMark(false);
            tagRepository.save(tag);
        }
    }
}
