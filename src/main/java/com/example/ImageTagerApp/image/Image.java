package com.example.ImageTagerApp.image;

import com.example.ImageTagerApp.tag.Tag;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false, unique = true)
    private Long imageId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "user_device_token")
    private String userDeviceToken;

    @OneToMany(mappedBy = "image", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private final List<Tag> tagList = new ArrayList<>();
}
