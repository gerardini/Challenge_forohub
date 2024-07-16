package com.aluraChallenge.forohub.infra.repository;

import com.aluraChallenge.forohub.domain.curso.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    boolean existsByName(String name);
}