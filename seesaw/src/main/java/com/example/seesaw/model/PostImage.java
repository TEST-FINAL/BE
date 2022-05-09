package com.example.seesaw.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;


    @ManyToOne //(fetch = FetchType.LAZY)
    @JoinColumn(name = "postid", nullable = false)
    private Post post;

    public PostImage(String imageUrls, Post post){
        this.imageUrl = imageUrls;
        this.post = post;
    }

}
