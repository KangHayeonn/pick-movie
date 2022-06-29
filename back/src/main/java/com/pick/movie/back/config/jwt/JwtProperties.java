package com.pick.movie.back.config.jwt;

public interface JwtProperties {
    String SECRET = "wekjglksldnwejnfjnlsdfnlwkneflkhnuvbnlksdrfwqcad"; // 우리 서버만 알고 있는 비밀값
    int EXPIRATION_TIME = 1000*10; // 3분

    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";

    int REFRESH_EXPIRATION_TIME = 1000*60*60*24*14; // 14일
    String REFRESH_HEADER_STRING = "Authorization";
}
