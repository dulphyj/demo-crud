package com.dlphsolutions.demo_crud.infrastructure.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.dlphsolutions.demo_crud.infrastructure.jwt.Constants.*;

@Service
public class JWTGenerator {

    public String getToken(String username){
        Date startDate = new Date(System.currentTimeMillis());
        Date expiredDate = new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION_TIME);
        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(
                SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().toString()
        );

        String token = Jwts.builder()
                .setId(ID_APP)
                .setSubject(username)
                .claim(AUTHORITIES,authorityList.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()) )
                .setIssuedAt(startDate)
                .setExpiration(expiredDate)
                .signWith(Constants.getSignedKey(Constants.SECRET_KEY), SignatureAlgorithm.HS512).compact();
        return TOKEN_BEARER + token;
    }
}
