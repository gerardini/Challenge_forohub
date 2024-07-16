package com.aluraChallenge.forohub.domain.curso;

public record CursoDTO(Long Id, String name, String category) {

    public CursoDTO(Curso curso) {
        this(
            curso.getId(),
            curso.getName(),
            curso.getCategory());
    }

}