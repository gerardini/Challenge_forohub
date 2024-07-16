package com.aluraChallenge.forohub.domain.curso;

import com.aluraChallenge.forohub.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Table(name = "cursos")
@Entity(name = "Curso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Topico> topicos;

    public Curso(CursoAddDTO cursoAddDTO) {
        this.name = cursoAddDTO.name();
        this.category = cursoAddDTO.category();
        this.topicos = new HashSet<>();
    }

    public void updateInfo(CursoAddDTO cursoAddDTO) {
        if (cursoAddDTO.name() != null) this.name = cursoAddDTO.name();
        if (cursoAddDTO.category() != null) this.category = cursoAddDTO.category();
    }
}
