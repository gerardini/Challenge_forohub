package com.aluraChallenge.forohub.domain.curso;

import jakarta.validation.constraints.NotBlank;

public record CursoAddDTO(@NotBlank
                          String name,
                          @NotBlank
                          String category
) {}