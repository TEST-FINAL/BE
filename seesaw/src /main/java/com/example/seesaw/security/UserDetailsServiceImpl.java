package com.example.seesaw.security;

import com.example.seesaw.user.model.User;
import com.example.seesaw.redis.RedisService;
import com.example.seesaw.user.repository.UserRepository;
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
    private final RedisService redisService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Can't find " + username));
        return new UserDetailsImpl(user);
    }

    public String saveRefreshToken(User user){
        User thisUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + user));
        String refreshToken = JwtTokenUtils.generateRefreshToken(thisUser);
        redisService.delRefreshValues(user.getUsername());
        redisService.setRefreshValues(user.getUsername(),refreshToken);
        return refreshToken;
    }
}
