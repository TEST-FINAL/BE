package com.example.seesaw.security;

import com.example.seesaw.model.RefreshToken;
import com.example.seesaw.model.User;
import com.example.seesaw.repository.RefreshTokenRepository;
import com.example.seesaw.repository.UserRepository;
import com.example.seesaw.security.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("user를 찾을 수 없어요");
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Can't find " + username));
        return new UserDetailsImpl(user);
    }

    public String saveRefershToken(User user){
        User thisUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + user));
        String refreshToken = JwtTokenUtils.generateRefreshToken(thisUser);
        RefreshToken userRefreshToken = new RefreshToken();
        userRefreshToken.setUser(thisUser);
        userRefreshToken.setRefreshToken(refreshToken);
        refreshTokenRepository.save(userRefreshToken);
        return refreshToken;
    }

//    public User findUser(String username) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException("이름을 찾을 수 없습니다."));
//        return user;
//    }
//
//    public void findPassword(String password) {
//    }
}
