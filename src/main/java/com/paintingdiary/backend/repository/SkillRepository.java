package com.paintingdiary.backend.repository;

import com.paintingdiary.backend.model.entity.Skill;
import com.paintingdiary.backend.model.entity.UserCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findAllByCharacter(UserCharacter character);
}
