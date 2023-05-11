package com.example.ImageTagerApp.tag;

import com.example.ImageTagerApp.bookMark.BookMark;
import com.example.ImageTagerApp.bookMark.BookMarkRepository;
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
    private final BookMarkRepository bookMarkRepository;


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
        final List<BookMark> bookMarks= bookMarkRepository.findAllByUserDeviceToken(userDeviceToken);

        for(BookMark bookMark: bookMarks) {
            result.add(bookMark.getBookMarkTagName());
        }

        return result;
    }


    //즐겨찾기에 새로운 태그 저장
    @Transactional
    void registerBookMarkTag(final String tagName, final String userDeviceToken){
        final BookMark bookMark= BookMark.builder()
                .bookMarkTagName(tagName)
                .userDeviceToken(userDeviceToken)
                .build();
        bookMarkRepository.save(bookMark);
    }


    //즐겨찾기에서 태그 삭제
    @Transactional
    void deleteBookMarkTag(final String tagName, final String userDeviceToken){
        BookMark bookMark= bookMarkRepository.findByBookMarkTagNameAndUserDeviceToken(tagName, userDeviceToken).get();
        bookMarkRepository.delete(bookMark);
    }
}
