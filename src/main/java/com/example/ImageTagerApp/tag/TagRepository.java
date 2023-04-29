package com.example.ImageTagerApp.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    List<Tag> findAllByTagNameAndUserDeviceToken(String tagName, String userDeviceToken);
    List<Tag> findAllByUserDeviceToken(String userDeviceToken);
    //List<Tag> findAllByImage(Image image);
}
