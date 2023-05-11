package com.example.ImageTagerApp.bookMark;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    List<BookMark> findAllByUserDeviceToken(String userDeviceToken);
    Optional<BookMark> findByBookMarkTagNameAndUserDeviceToken(String bookMarkTagName, String userDeviceToken);
}