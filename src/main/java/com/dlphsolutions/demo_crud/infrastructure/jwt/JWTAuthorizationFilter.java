package com.dlphsolutions.demo_crud.infrastructure.jwt;

import com.dlphsolutions.demo_crud.application.service.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.dlphsolutions.demo_crud.infrastructure.jwt.Constants.AUTHORITIES;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private final CustomUserDetailService customUserDetailService;

    public JWTAuthorizationFilter(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            if(JWTValidate.tokenExists(request, response)){
                Claims claims = JWTValidate.JWTValid(request);
                if (claims.get(AUTHORITIES) != null){
                    JWTValidate.setAuthentication(claims, customUserDetailService);
                }else
                {
                    SecurityContextHolder.clearContext();
                }
            }else{
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(request, response);

        }catch (ExpiredJwtException e){
            handleException(response, "Token expired", HttpServletResponse.SC_UNAUTHORIZED);
        } catch (UnsupportedJwtException | MalformedJwtException e){
            handleException(response, "Invalid token", HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e){
            handleException(response, "Internal server error", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void handleException(HttpServletResponse response, String message, int status) throws IOException{
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
