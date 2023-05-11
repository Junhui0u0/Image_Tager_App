package com.example.ImageTagerApp.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    void deleteAllByUserDeviceToken(String userDeviceToken);
    List<Image> findAllByUserDeviceToken(String userDeviceToken);
}
