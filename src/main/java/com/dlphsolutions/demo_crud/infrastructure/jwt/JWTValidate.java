package com.dlphsolutions.demo_crud.infrastructure.jwt;

import com.dlphsolutions.demo_crud.application.service.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class JWTValidate {

    public static boolean tokenExists(HttpServletRequest request, HttpServletResponse response){
        String header = request.getHeader(Constants.HEADER_AUTHORIZATION);
        if(header == null || !header.startsWith(Constants.TOKEN_BEARER)) return false;
        return true;
    }

    public static Claims JWTValid(HttpServletRequest request){
        String jwtToken = request.getHeader(Constants.HEADER_AUTHORIZATION).replace(Constants.TOKEN_BEARER, "");
        return Jwts.parserBuilder()
                .setSigningKey(Constants.getSignedKey(Constants.SECRET_KEY))
                .build()
                .parseClaimsJws(jwtToken).getBody();
    }

    public static void setAuthentication(Claims claims, CustomUserDetailService customUserDetailService){
        UserDetails userDetails = customUserDetailService.loadUserByUsername(claims.getSubject());
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
