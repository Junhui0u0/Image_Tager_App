package com.example.ImageTagerApp.bookMark;

import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="book_marks")
public class BookMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_mark_id", nullable = false, unique = true)
    private Long bookMarkId;

    @Column(name = "book_mark_tag_name")
    private String bookMarkTagName;

    @Column(name = "user_device_token")
    private String userDeviceToken;
}
