package com.example.ImageTagerApp.image;


import com.example.ImageTagerApp.config.S3.S3Uploader;
import com.example.ImageTagerApp.image.dto.ImageDto;
import com.example.ImageTagerApp.image.dto.ImageListDto;
import com.example.ImageTagerApp.image.dto.SearchDto;
import com.example.ImageTagerApp.tag.Tag;
import com.example.ImageTagerApp.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final TagRepository tagRepository;
    private final S3Uploader s3Uploader;

    @Value("${cloud.aws.s3.bucket.url}")
    private String AWS_S3_BUCKET_URL;


    //갤러리에 있는 캡쳐사진 저장
    @Transactional
    public void registerImages(final List<MultipartFile> images, final String userDeviceToken){
        for (MultipartFile multipartFile : images) {
            final Image image = Image.builder()
                    .userDeviceToken(userDeviceToken)
                    .imageUrl(s3Uploader.upload(multipartFile, "image"))
                    .build();
            imageRepository.save(image);


            //사진을 대표하는 태그 속성 저장
            for (int i = 0; i < 3; i++) {
                final Tag tag = Tag.builder()
                        .userDeviceToken(userDeviceToken)
                        .tagName("tag"+(i+1))
                        .flagBookMark(false)
                        .image(image)
                        .build();
                tagRepository.save(tag);
            }
        }
    }


    //사진 조회
    @Transactional(readOnly = true)
    ImageDto getImage(final Long imageId){
        final Image image= imageRepository.findById(imageId).get();
        final List<String> tagNames= new ArrayList<>();
        //for(Tag tag: tagRepository.findAllByImage(image)) tagNames.add(tag.getTagName());
        for(Tag tag: image.getTagList()) tagNames.add(tag.getTagName());

        return ImageDto.builder()
                .imageId(image.getImageId())
                .imageUrl(image.getImageUrl())
                .tags(tagNames)
                .build();
    }


    //사진 태그 추가
    @Transactional
    void registerImageTag(final Long imageId, final String tagName){
        final Image image= imageRepository.findById(imageId).get();
        final Tag tag= Tag.builder()
                .userDeviceToken(image.getUserDeviceToken())
                .tagName(tagName)
                .flagBookMark(false)
                .image(image)
                .build();
        tagRepository.save(tag);
    }


    //사진 태그 삭제
    @Transactional
    void deleteImageTag(final Long tagId){
        tagRepository.deleteById(tagId);
    }


    //검색한 태그에 해당하는 사진 조회
    @Transactional(readOnly = true)
    List<ImageListDto> getImagesByTag(final SearchDto searchDto){
        final List<ImageListDto> result= new ArrayList<>();
        final HashMap<Long,Integer> duplicateChk= new HashMap<>();

        for(String tagName: searchDto.getTagName()){
            final List<Tag> tags= tagRepository.findAllByTagNameAndUserDeviceToken(tagName, searchDto.getUserDeviceToken());
            for(Tag tag: tags){
                if(duplicateChk.containsKey(tag.getImage().getImageId())) continue;
                final ImageListDto imageListDto= ImageListDto.builder()
                        .imageId(tag.getImage().getImageId())
                        .imageUrl(tag.getImage().getImageUrl())
                        .build();
                result.add(imageListDto);
                duplicateChk.put(tag.getImage().getImageId(),1);
            }
        }
        return result;
    }
}

//이미지에 이름이 같은 태그가 존재하지 않다고 가정