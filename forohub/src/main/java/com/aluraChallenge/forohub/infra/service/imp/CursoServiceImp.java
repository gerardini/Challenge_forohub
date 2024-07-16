package com.aluraChallenge.forohub.infra.service.imp;

import com.aluraChallenge.forohub.domain.curso.CursoAddDTO;
import com.aluraChallenge.forohub.domain.curso.CursoDTO;
import com.aluraChallenge.forohub.domain.curso.Curso;
import com.aluraChallenge.forohub.infra.repository.CursoRepository;
import com.aluraChallenge.forohub.infra.service.CursoService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CursoServiceImp implements CursoService {

    private final CursoRepository cursoRepository;

    @Autowired
    public CursoServiceImp(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Override
    public Page<CursoDTO> getAllCursos(Pageable pageable) {
        return cursoRepository.findAll(pageable).map(CursoDTO::new);
    }

    @Override
    public CursoDTO getCurso(Long id) {
        var curso = cursoRepository.findById(id);
        return curso.map(CursoDTO::new).orElse(null);
    }

    @Override
    @Transactional
    public CursoDTO createCurso(CursoAddDTO cursoAddDTO) {
        if (cursoRepository.existsByName(cursoAddDTO.name())) throw new EntityExistsException("un curso con esta Id ya existe");

        var curso = new Curso(cursoAddDTO);
        cursoRepository.save(curso);
        return new CursoDTO(curso);
    }

    @Override
    @Transactional
    public CursoDTO updateCurso(Long id, CursoAddDTO cursoAddDTO) {
        var optionalCurso = cursoRepository.findById(id);
        if (optionalCurso.isEmpty()) return null;

        var curso = optionalCurso.get();
        curso.updateInfo(cursoAddDTO);

        return new CursoDTO(curso);
    }


    @Override
    @Transactional
    public void deleteCurso(Long id) {
        var optionalCurso = cursoRepository.findById(id);
        if (optionalCurso.isPresent())
            cursoRepository.delete(optionalCurso.get());
        else
            throw new EntityNotFoundException("un curso con la id: " + id + " no existe");
    }
}

