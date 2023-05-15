package com.example.ImageTagerApp.tag;

import com.example.ImageTagerApp.image.Image;
import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id", nullable = false, unique = true)
    private Long tagId;

    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "user_device_token")
    private String userDeviceToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="image_id")
    private Image image;

}
