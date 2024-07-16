package com.aluraChallenge.forohub.controller;

import com.aluraChallenge.forohub.domain.curso.CursoAddDTO;
import com.aluraChallenge.forohub.domain.curso.CursoDTO;
import com.aluraChallenge.forohub.infra.service.CursoService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/con/cursos")
public class CursoController {

    private final CursoService cursoService;

    @Autowired
    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    @Operation(summary = "Obtener el listado de los cursos")
    public ResponseEntity<Page<CursoDTO>> getCourses(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(cursoService.getAllCursos(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener los detalles sobre un curso a partir del Id")
    public ResponseEntity<CursoDTO> getCourse(@PathVariable Long id) {
        var curso = cursoService.getCurso(id);
        if (curso == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(curso);
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo curso")
    public ResponseEntity<CursoDTO> createCourse(@RequestBody @Valid CursoAddDTO cursoAddDTO) {
        var curso = cursoService.createCurso(cursoAddDTO);
        var uri = UriComponentsBuilder.fromPath("/cursos/{id}").buildAndExpand(curso.Id()).toUri();
        return ResponseEntity.created(uri).body(curso);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar informacion de un curso. Campos editables: name, category")
    public ResponseEntity<CursoDTO> updateCourse(@RequestBody CursoAddDTO cursoAddDTO, @PathVariable Long id) {
        var curso= cursoService.updateCurso(id, cursoAddDTO);
        if (curso == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(curso);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un curso a partir del Id")
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
        cursoService.deleteCurso(id);
        return ResponseEntity.noContent().build();
    }

}