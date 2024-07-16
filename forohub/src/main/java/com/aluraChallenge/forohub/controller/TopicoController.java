package com.aluraChallenge.forohub.controller;

import com.aluraChallenge.forohub.domain.reply.ReplyAddDTO;
import com.aluraChallenge.forohub.domain.reply.ReplyDTO;
import com.aluraChallenge.forohub.domain.reply.ReplyUpdateDTO;
import com.aluraChallenge.forohub.domain.topico.TopicoAddDTO;
import com.aluraChallenge.forohub.domain.topico.TopicoDTO;
import com.aluraChallenge.forohub.infra.service.TopicoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/con/topicos")
public class TopicoController {
    private final TopicoService topicoService;

    @Autowired
    public TopicoController(TopicoService topicoService) {
        this.topicoService = topicoService;
    }

    @GetMapping
    @Operation(summary = "Obtener listado de los topicos")
    public ResponseEntity<Page<TopicoDTO>> getTopicos(Pageable pageable) {
        return ResponseEntity.ok(topicoService.getAllTopicos(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un topico con el id")
    public ResponseEntity<TopicoDTO> getTopico(@PathVariable Long id) {
        var topico = topicoService.getTopico(id);
        if (topico == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(topico);
    }

    @PostMapping
    @Operation(summary = "Crear topico")
    public ResponseEntity<TopicoDTO> createTopico(@RequestBody @Valid TopicoAddDTO topicoAddDTO) {
        var topico = topicoService.createTopico(topicoAddDTO);
        var uri = UriComponentsBuilder.fromPath("/topicos/{id}").buildAndExpand(topico.Id()).toUri();
        return ResponseEntity.created(uri).body(topico);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar un topico buscandolo con el id.")
    public ResponseEntity<TopicoDTO> updateTopico(@PathVariable Long id, @RequestBody TopicoAddDTO topicoAddDTO) {
        var topico = topicoService.updateTopico(id, topicoAddDTO);
        if (topico == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(topico);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un topico buscado con el Id")
    public ResponseEntity<Void> deleteTopico(@PathVariable Long id) {
        topicoService.deleteTopico(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/replies")
    @Operation(summary = "Obtener el listado de respuestas de un topico")
    public ResponseEntity<Page<ReplyDTO>> getReplies(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(topicoService.getReplies(id, pageable));
    }

    @PostMapping("/{id}/replies")
    @Operation(summary = "Agregar una respuesta a un topico")
    public ResponseEntity<ReplyDTO> addReply(@PathVariable Long id, @RequestBody @Valid ReplyAddDTO replyAddDTO) {
        var reply = topicoService.addReply(id, replyAddDTO);
        if (reply == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reply);
    }

    @PutMapping("/{id}/replies/{replyId}")
    @Operation(summary = "Editar una respuesta de un topico")
    public ResponseEntity<ReplyDTO> updateReply(@PathVariable Long id, @PathVariable Long replyId, @RequestBody @Valid ReplyUpdateDTO replyUpdateDTO) {
        var reply = topicoService.updateReply(id, replyId, replyUpdateDTO);
        if (reply == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reply);
    }

    @DeleteMapping("/{id}/replies/{replyId}")
    @Operation(summary = "Eliminar una respuesta de un topico")
    public ResponseEntity<Void> deleteReply(@PathVariable Long id, @PathVariable Long replyId) {
        topicoService.removeReply(id, replyId);
        return ResponseEntity.noContent().build();
    }
}
