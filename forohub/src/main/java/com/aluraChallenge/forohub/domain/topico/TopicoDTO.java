package com.aluraChallenge.forohub.domain.topico;

import java.time.LocalDateTime;

public record TopicoDTO(Long Id,
                        String title,
                        String content,
                        String author,
                        String curso,
                        LocalDateTime createdAt,
                        Integer numResponses
) {

    public TopicoDTO(Topico topico) {
        this(topico.getId(),
                topico.getTitle(),
                topico.getContent(),
                topico.getAuthor().getUsername(),
                topico.getCurso().getName(),
                topico.getCreatedAt(),
                topico.getNumResponses());
    }

}