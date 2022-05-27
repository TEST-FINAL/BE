package com.example.seesaw.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.seesaw.user.model.User;

import java.util.Date;

public final class JwtTokenUtils {

    private static final int SEC = 1;
    private static final int MINUTE = 60 * SEC;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;

    // JWT 토큰의 유효기간: 3일 (단위: seconds)
    private static final int JWT_TOKEN_VALID_SEC = 3 * DAY;
    // JWT 토큰의 유효기간: 3일 (단위: milliseconds)
    private static final int JWT_TOKEN_VALID_MILLI_SEC = JWT_TOKEN_VALID_SEC * 1000;

    // JWT 토큰의 유효기간: 7일 (단위: seconds)
    private static final int REFRESH_TOKEN_VALID_SEC = 7 * DAY;
    // JWT 토큰의 유효기간: 7일 (단위: milliseconds)
    private static final int REFRESH_TOKEN_VALID_MILLI_SEC = REFRESH_TOKEN_VALID_SEC * 1000;

    public static final String CLAIM_EXPIRED_DATE = "EXPIRED_DATE";
    public static final String CLAIM_USER_NAME = "USER_NAME";
    public static final String UID = "UID";
    public static final String NICKNAME = "NICKNAME";
    public static final String GENERATION = "GENERATION";
    public static final String MBTI = "MBTI";
    public static final String JWT_SECRET = "jwt_secret_!@#$%";

    public static String generateJwtToken(User user) {
        String accessToken = null;

        try {
            accessToken = JWT.create()
                    .withIssuer("sparta")
                    .withClaim(CLAIM_USER_NAME, user.getUsername())
                     // 토큰 만료 일시 = 현재 시간 + 토큰 유효기간)
                    .withClaim(CLAIM_EXPIRED_DATE, new Date(System.currentTimeMillis() + JWT_TOKEN_VALID_MILLI_SEC))
                    .withClaim(UID, user.getId())
                    .withClaim(NICKNAME, user.getNickname())
                    .withClaim(GENERATION, user.getGeneration())
                    .withClaim(MBTI, user.getMbti())
                    .sign(generateAlgorithm());
            System.out.println("accessToken 생성 : "+ accessToken);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return accessToken;
    }

    public static String generateRefreshToken(User user) {

        String refreshToken = null;

        try {
            refreshToken = JWT.create()
                    .withIssuer("sparta")
                    .withClaim(CLAIM_USER_NAME, user.getUsername())
                    // 토큰 만료 일시 = 현재 시간 + 토큰 유효기간)
                    .withClaim(CLAIM_EXPIRED_DATE, new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALID_MILLI_SEC))
                    .withClaim(UID, user.getId())
                    .withClaim(NICKNAME, user.getNickname())
                    .sign(generateAlgorithm());
            System.out.println("refreshToken 생성 : "+ refreshToken);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return refreshToken;
    }

    private static Algorithm generateAlgorithm() {
        return Algorithm.HMAC256(JWT_SECRET);
    }
}
