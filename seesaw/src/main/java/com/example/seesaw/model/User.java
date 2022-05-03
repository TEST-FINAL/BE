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
public class User {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String pwd;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column
    private String generation;

    @Column
    private Long postCount;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column(unique = true)
    private Long kakaoId;


    public User(String username, String nickname, String enPassword, String generation, Long postCount, UserRoleEnum role) {
        this.username = username;
        this.nickname = nickname;
        this.pwd = enPassword;
        this.generation = generation;
        this.postCount = postCount;
        this.role = role;
        this.kakaoId = null;
    }

    public User(String username, String nickname, String enPassword, String generation, Long postCount, UserRoleEnum role, Long kakaoId) {
        this.username = username;
        this.nickname = nickname;
        this.pwd = enPassword;
        this.generation = generation;
        this.postCount = postCount;
        this.role = role;
        this.kakaoId = kakaoId;
    }
}
