package com.paintingdiary.backend.repository;

import com.paintingdiary.backend.model.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findByUid(String uid);

    Optional<Auth> findByEmail(String email);
}
