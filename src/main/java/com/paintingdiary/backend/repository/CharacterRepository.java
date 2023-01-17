package com.paintingdiary.backend.repository;

import com.paintingdiary.backend.model.entity.User;
import com.paintingdiary.backend.model.entity.UserCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<UserCharacter, Long> {
    Optional<UserCharacter> findByUser(User user);
}
