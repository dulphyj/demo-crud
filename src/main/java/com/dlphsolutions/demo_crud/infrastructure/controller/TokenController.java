package com.dlphsolutions.demo_crud.infrastructure.controller;

import com.dlphsolutions.demo_crud.infrastructure.jwt.JWTGenerator;
import com.dlphsolutions.demo_crud.domain.model.User;
import com.dlphsolutions.demo_crud.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Token", description = "Operations pertaining to token")
public class TokenController {
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;

    public TokenController(AuthenticationManager authenticationManager, JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }

    @Operation(summary = "Get token; The ID field is not required")
    @PostMapping("/gettoken")
    public ResponseEntity<?> getToken(@RequestBody User user){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUser(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.getToken(user.getUser());
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, "Token generated successfully", token));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(HttpStatus.UNAUTHORIZED, "Invalid credentials", null));
        }


    }
}
