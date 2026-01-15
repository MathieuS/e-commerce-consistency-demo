package com.github.mathieus.customerportal.repository;

import com.github.mathieus.customerportal.entity.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxRepository extends JpaRepository<Outbox, Long> {

    List<Outbox> findTop50ByStatusOrderByCreatedAtAsc(String status);
}
