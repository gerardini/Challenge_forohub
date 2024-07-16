package com.aluraChallenge.forohub.domain.reply;

import java.time.LocalDateTime;

public record ReplyDTO(Long Id, String message, LocalDateTime createdAt, Long repliesTo, String User) {

    public ReplyDTO(Reply reply) {
        this(reply.getId(),
             reply.getMessage(),
             reply.getCreatedAt(),
             reply.getRepliesTo(),
             reply.getUser().getUsername());
    }

}

