package com.example.seesaw.dictionary.model;

import com.example.seesaw.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PostCommentLike extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "postCommentId")
    private PostComment postComment;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;


    public PostCommentLike(PostComment postComment, User user) {
        this.postComment = postComment;
        this.user = user;
    }

}
