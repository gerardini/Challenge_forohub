package com.aluraChallenge.forohub.infra.service.imp;


import com.aluraChallenge.forohub.domain.curso.Curso;
import com.aluraChallenge.forohub.domain.reply.Reply;
import com.aluraChallenge.forohub.domain.reply.ReplyAddDTO;
import com.aluraChallenge.forohub.domain.reply.ReplyDTO;
import com.aluraChallenge.forohub.domain.reply.ReplyUpdateDTO;
import com.aluraChallenge.forohub.domain.topico.Topico;
import com.aluraChallenge.forohub.domain.topico.TopicoAddDTO;
import com.aluraChallenge.forohub.domain.topico.TopicoDTO;
import com.aluraChallenge.forohub.infra.repository.CursoRepository;
import com.aluraChallenge.forohub.infra.repository.ReplyRepository;
import com.aluraChallenge.forohub.infra.repository.TopicoRepository;
import com.aluraChallenge.forohub.infra.repository.UserRepository;
import com.aluraChallenge.forohub.infra.service.TopicoService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TopicoServiceImp implements TopicoService {

    private final TopicoRepository topicoRepository;
    private final UserRepository userRepository;
    private final CursoRepository cursoRepository;
    private final ReplyRepository replyRepository;

    @Autowired
    public TopicoServiceImp(TopicoRepository topicoRepository, UserRepository userRepository, CursoRepository cursoRepository, ReplyRepository replyRepository) {
        this.topicoRepository = topicoRepository;
        this.userRepository = userRepository;
        this.cursoRepository = cursoRepository;
        this.replyRepository = replyRepository;
    }

    @Override
    public Page<TopicoDTO> getAllTopicos(Pageable pageable) {
        return topicoRepository.findAll(pageable).map(TopicoDTO::new);
    }

    @Override
    public TopicoDTO getTopico(Long id) {
        return topicoRepository.findById(id).map(TopicoDTO::new).orElse(null);
    }

    @Override
    @Transactional
    public TopicoDTO createTopico(TopicoAddDTO topicoAddDTO) {
        if (topicoRepository.existsByTitle(topicoAddDTO.title()))
            throw new EntityExistsException("Un topico con el titulo:  \"" + topicoAddDTO.title() + "\"  ya existe");

        var user = userRepository.findById(topicoAddDTO.userId());
        var curso = cursoRepository.findById(topicoAddDTO.cursoId());

        if (user.isEmpty())
            throw new  ValidationException("EL usuario con id:" + topicoAddDTO.userId() + " no existe");

        if (curso.isEmpty())
            throw new  ValidationException("El curso con id; " + topicoAddDTO.cursoId() + " no existe");

        var topico = new Topico(topicoAddDTO, user.get(), curso.get());
        topicoRepository.save(topico);

        return new TopicoDTO(topico);
    }

    @Override
    @Transactional
    public TopicoDTO updateTopico(Long id, TopicoAddDTO topicoAddDTO) {
        var optionalTopico = topicoRepository.findById(id);

        if (optionalTopico.isEmpty())
            throw new EntityNotFoundException("Topico con id: " + id + " no encontrado");

        Curso curso = null;

        if (topicoAddDTO.cursoId() != null) {
            var optionalCurso = cursoRepository.findById(topicoAddDTO.cursoId());
            if (optionalCurso.isEmpty())
                throw new  ValidationException("un curso con la  id: " + topicoAddDTO.cursoId() + " no existe");

            curso = optionalCurso.get();
        }

        var topico = optionalTopico.get();
        topico.updateInfo(topicoAddDTO, curso);

        return new TopicoDTO(topico);
    }

    @Override
    @Transactional
    public void deleteTopico(Long id) {
        var optionalTopico = topicoRepository.findById(id);

        if (optionalTopico.isPresent())
            topicoRepository.delete(optionalTopico.get());
        else
            throw new EntityNotFoundException("El topico con id:" + id + " no existe");
    }

    @Override
    public Page<ReplyDTO> getReplies(Long topicoId, Pageable pageable) {
        if (!topicoRepository.existsById(topicoId))
            throw new EntityNotFoundException("el topico con id:  " + topicoId + " no existe");

        return replyRepository.findAllByTopicoId(topicoId, pageable).map(ReplyDTO::new);
    }

    @Override
    @Transactional
    public ReplyDTO addReply(Long topicoId, ReplyAddDTO replyAddDTO) {

        var optionalTopico = topicoRepository.findById(topicoId);
        if (optionalTopico.isEmpty())
            throw new EntityNotFoundException("El topico con id " + topicoId + " no existe");

        var optionalUser = userRepository.findById(replyAddDTO.userId());
        if (optionalUser.isEmpty())
            throw new  ValidationException("El user con id: " + replyAddDTO.userId() + " no existe");

        var reply = new Reply(replyAddDTO, optionalTopico.get(), optionalUser.get());
        replyRepository.save(reply);

        return new ReplyDTO(reply);
    }

    @Override
    @Transactional
    public ReplyDTO updateReply(Long topicoId, Long replyId, ReplyUpdateDTO replyEditDTO) {
        if (!topicoRepository.existsById(topicoId))
            throw new EntityNotFoundException("El topico con id: " + topicoId + " no existe");

        var optionalReply = replyRepository.findById(replyId);
        if (optionalReply.isEmpty()) return null;

        var reply = optionalReply.get();
        reply.UpdateInfo(replyEditDTO);

        return new ReplyDTO(reply);
    }

    @Override
    @Transactional
    public void removeReply(Long topicoId, Long replyId) {
        if (!topicoRepository.existsById(topicoId))
            throw new EntityNotFoundException("El topico con id: " + topicoId + " no existe");

        var reply = replyRepository.findById(replyId);
        if (reply.isPresent())
            replyRepository.delete(reply.get());
        else
            throw new EntityNotFoundException("la respuesta con id: " + replyId + " no existe");
    }
}

