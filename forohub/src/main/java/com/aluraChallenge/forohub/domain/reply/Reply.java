package com.aluraChallenge.forohub.domain.reply;

import com.aluraChallenge.forohub.domain.topico.Topico;
import com.aluraChallenge.forohub.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "replies")
@Entity(name = "Reply")
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private LocalDateTime createdAt;
    private Long repliesTo;

    @ManyToOne
    @JoinColumn(name = "topico_id", nullable = false)
    private Topico topico;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Reply(ReplyAddDTO replyAddDTO, Topico topico, User user) {
        this.message = replyAddDTO.message();
        this.createdAt = LocalDateTime.now();

        if (replyAddDTO.repliesTo() == null) this.repliesTo = 0L; else this.repliesTo = replyAddDTO.repliesTo();

        this.topico = topico;
        this.user = user;
    }

    public void UpdateInfo(ReplyUpdateDTO replyUpdateDTO) {
        this.message = replyUpdateDTO.message();
    }

}