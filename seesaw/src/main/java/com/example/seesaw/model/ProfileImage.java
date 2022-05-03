package com.example.seesaw.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ProfileImage extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String imageUrls;

    //@JsonIgnore
    @ManyToOne //(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public ProfileImage(String imageUrls, User item){
        this.imageUrls = imageUrls;
        this.user = item;
    }
}
