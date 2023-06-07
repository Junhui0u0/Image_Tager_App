package com.example.ImageTagerApp.image;


import com.example.ImageTagerApp.config.S3.S3Uploader;
import com.example.ImageTagerApp.image.dto.ImageDto;
import com.example.ImageTagerApp.image.dto.ImageListDto;
import com.example.ImageTagerApp.image.dto.SearchDto;
import com.example.ImageTagerApp.tag.Tag;
import com.example.ImageTagerApp.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void registerImages(final List<MultipartFile> images, final String userDeviceToken, Map<String,List<String>> inferredTagList){
        for (int i=0; i<images.size(); i++) {
            final Image image = Image.builder()
                    .userDeviceToken(userDeviceToken)
                    .imageUrl(s3Uploader.upload(images.get(i), "image"))
                    .build();
            imageRepository.save(image);


            //사진을 대표하는 태그 속성 저장
            for (String inferredTag: inferredTagList.get(String.valueOf(i+1))) {
                final Tag tag = Tag.builder()
                        .userDeviceToken(userDeviceToken)
                        .tagName(inferredTag)
                        .image(image)
                        .build();
                tagRepository.save(tag);
            }
        }
    }


    //갤러리에 있는 캡쳐사진 저장 (AI서버 연결X)
    @Transactional
    public void registerImagesByRandomTag(final List<MultipartFile> images, final String userDeviceToken){
        imageRepository.deleteAllByUserDeviceToken(userDeviceToken);
        List<Image> imageList= imageRepository.findAllByUserDeviceToken(userDeviceToken);
        for(Image deleteImage: imageList){
            s3Uploader.deleteFile(deleteImage.getImageUrl().substring(AWS_S3_BUCKET_URL.length()));
        }

        for (int i=0; i<images.size(); i++) {
            final Image image = Image.builder()
                    .userDeviceToken(userDeviceToken)
                    .imageUrl(s3Uploader.upload(images.get(i), "image"))
                    .build();
            imageRepository.save(image);


		//사진을 대표하는 태그 속성 저장
            for (int j = 0; j < 3; j++) {
                final Tag tag = Tag.builder()
                        .userDeviceToken(userDeviceToken)
                        .tagName("tag"+(j+1))
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
        final List<Pair<Long,String>> tagNames= new ArrayList<>();
        for(Tag tag: image.getTagList()) tagNames.add(Pair.of(tag.getTagId(), tag.getTagName()));

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
    List<ImageDto> getImagesByTag(final String userDeviceToken, final List<String> tags){
        final List<ImageDto> result= new ArrayList<>();
        final HashMap<Long,Integer> duplicateChk= new HashMap<>();

        for(String tagName: tags){
            final List<Tag> tagList= tagRepository.findAllByTagNameAndUserDeviceToken(tagName, userDeviceToken);

            for(Tag tag: tagList){
                final List<Pair<Long,String>> tagListToImage= new ArrayList<>();
                if(duplicateChk.containsKey(tag.getImage().getImageId())) continue;

                for(Tag t: tag.getImage().getTagList()){
                    tagListToImage.add(Pair.of(t.getTagId(), t.getTagName()));
                }

                final ImageDto imageDto= ImageDto.builder()
                        .imageId(tag.getImage().getImageId())
                        .imageUrl(tag.getImage().getImageUrl())
                        .tags(tagListToImage)
                        .build();
                result.add(imageDto);

                duplicateChk.put(tag.getImage().getImageId(),1);
            }
        }
        return result;
    }

    //예전에 저장된 캡쳐사진 모두 삭제
    @Transactional
    void deleteAllImage(final String userDeviceToken){
        imageRepository.deleteAllByUserDeviceToken(userDeviceToken);
    }
}

//이미지에 이름이 같은 태그가 존재하지 않다고 가정
