package com.dlphsolutions.demo_crud.infrastructure.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.security.Key;

public class Constants {
    public static final String ID_APP = "dlphsolutions";
    public static final String AUTHORITIES = "authorities";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String TOKEN_BEARER = "Bearer ";
    public static final long TOKEN_EXPIRATION_TIME = 1500000; //15min
    public static final String SECRET_KEY = "438y4iaurhtrq43uiaer78ry4uifga4fruiag4789gdkjh34598ewjhsdkf23oifsdoif34lksdfoi3foeh32232";

    public static Key getSignedKey(String secretKey){
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
