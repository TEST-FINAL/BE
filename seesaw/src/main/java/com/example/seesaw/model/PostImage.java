package com.example.seesaw.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String postImage;

    @ManyToOne
    @JoinColumn(name = "postid", nullable = false)
    private Post post;


    public PostImage(String postImage, Post post){
        this.postImage = postImage;
        this.post = post;
    }

}
