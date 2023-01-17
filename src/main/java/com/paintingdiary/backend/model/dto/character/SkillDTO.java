package com.paintingdiary.backend.model.dto.character;

import com.paintingdiary.backend.model.entity.Skill;

public record SkillDTO(
        String title,
        String description,
        int progress
) {
    public static SkillDTO of(Skill skill) {
        if (skill == null) {
            return null;
        }

        return new SkillDTO(skill.getTitle(), skill.getDescription(), skill.getProgress());
    }
}
