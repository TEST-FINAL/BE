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
public class UserProfileNum {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USERPROFILE_ID")
    private UserProfile userProfile;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public UserProfileNum(UserProfile userProfile, User user){
        this.userProfile = userProfile;
        this.user = user;
    }
}
