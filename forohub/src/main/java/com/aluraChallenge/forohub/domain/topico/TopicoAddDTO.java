package com.aluraChallenge.forohub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicoAddDTO(
        @NotBlank
        String title,
        @NotBlank
        String content,
        @NotNull
        Long userId,
        @NotNull
        Long cursoId
) {}
