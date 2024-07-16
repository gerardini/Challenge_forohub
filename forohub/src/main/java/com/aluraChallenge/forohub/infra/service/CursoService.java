package com.aluraChallenge.forohub.infra.service;
import com.aluraChallenge.forohub.domain.curso.CursoAddDTO;
import com.aluraChallenge.forohub.domain.curso.CursoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CursoService {
    Page<CursoDTO> getAllCursos(Pageable pageable);
    CursoDTO getCurso(Long id);
    CursoDTO createCurso(CursoAddDTO cursoAddDTO);
    CursoDTO updateCurso(Long id, CursoAddDTO cursoAddDTO);
    void deleteCurso(Long id);
}