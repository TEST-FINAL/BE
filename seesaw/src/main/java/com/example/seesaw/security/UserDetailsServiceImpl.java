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
        refreshTokenRepository.deleteByUserId(user.getId());  //id 값은 그대로이면서 수정되는것으로 변경하기
        refreshTokenRepository.save(userRefreshToken);
        return refreshToken;
    }
}
