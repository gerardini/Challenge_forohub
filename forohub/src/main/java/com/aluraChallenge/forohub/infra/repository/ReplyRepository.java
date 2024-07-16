package com.aluraChallenge.forohub.infra.repository;
import com.aluraChallenge.forohub.domain.reply.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Page<Reply> findAllByTopicoId(Long topicoId, Pageable pageable);
}