package com.aluraChallenge.forohub.infra.service;

import com.aluraChallenge.forohub.domain.user.User;

public interface TokenService {
    String getToken(User user);
    String getSubject(String token);
}
