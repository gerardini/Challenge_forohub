package com.aluraChallenge.forohub.domain.user;

import jakarta.validation.constraints.NotBlank;

public record UserUpdatePasswordDTO(
        @NotBlank
        Long Id,
        @NotBlank
        String password
) {}