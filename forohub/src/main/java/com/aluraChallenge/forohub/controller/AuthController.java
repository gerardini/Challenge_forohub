package com.aluraChallenge.forohub.controller;

import com.aluraChallenge.forohub.domain.user.AuthUserDTO;
import com.aluraChallenge.forohub.domain.token.JwtTokenDTO;
import com.aluraChallenge.forohub.domain.user.User;
import com.aluraChallenge.forohub.infra.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


    @RestController
    @RequestMapping("/login")
    public class AuthController {

        private final AuthenticationManager authenticationManager;
        private final TokenService tokenService;

        @Autowired
        public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
            this.authenticationManager = authenticationManager;
            this.tokenService = tokenService;
        }

        @PostMapping
        @Operation(summary = "API Login con JWT")
        public ResponseEntity<JwtTokenDTO> authenticate(@RequestBody @Valid AuthUserDTO authUserDTO) {
            var authToken = new UsernamePasswordAuthenticationToken(authUserDTO.username(), authUserDTO.password());

            var authUser = authenticationManager.authenticate(authToken);
            var jtwToken = tokenService.getToken((User) authUser.getPrincipal());

            return ResponseEntity.ok(new JwtTokenDTO(jtwToken));
        }

    }

