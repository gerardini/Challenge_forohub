package com.aluraChallenge.forohub.domain.reply;

import jakarta.validation.constraints.NotBlank;

public record ReplyUpdateDTO(@NotBlank String message) {}
