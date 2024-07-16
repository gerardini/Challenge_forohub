package com.aluraChallenge.forohub.domain.topico;
import com.aluraChallenge.forohub.domain.curso.Curso;
import com.aluraChallenge.forohub.domain.reply.Reply;
import com.aluraChallenge.forohub.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;
    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;
    private Integer numResponses;
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Reply> replies;

    public Topico(TopicoAddDTO topicoAddDTO, User author, Curso curso) {
        this.title = topicoAddDTO.title();
        this.content = topicoAddDTO.content();
        this.author = author;
        this.curso = curso;
        this.numResponses = 0;
        this.createdAt = LocalDateTime.now();
        this.replies = new HashSet<>();
    }

    public void updateInfo(TopicoAddDTO topicoAddDTO,  Curso curso) {
        if (topicoAddDTO.title() != null) this.title = topicoAddDTO.title();
        if (topicoAddDTO.content() != null) this.content = topicoAddDTO.content();
        if (topicoAddDTO.cursoId() != null) this.curso = curso;
    }
}

