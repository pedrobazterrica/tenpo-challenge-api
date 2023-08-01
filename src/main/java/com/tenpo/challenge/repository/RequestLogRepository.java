package com.tenpo.challenge.repository;

import com.tenpo.challenge.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RequestLogRepository extends JpaRepository<Request, Integer> {
}
