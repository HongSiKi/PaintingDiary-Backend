package com.paintingdiary.backend.model.dto.character;

import com.paintingdiary.backend.model.dto.UserDTO;
import com.paintingdiary.backend.model.entity.UserCharacter;
import jakarta.annotation.Nullable;

import java.util.List;

public record CharacterDTO(
        UserDTO user,
        String nickname,
        @Nullable String link,
        List<SkillDTO> skillList
) {
    public static CharacterDTO of(UserCharacter character) {
        if (character == null) {
            return null;
        }

        return new CharacterDTO(
                UserDTO.of(character.getUser(), true),
                character.getNickname(),
                character.getLink(),
                character.getSkillList().stream().map(SkillDTO::of).toList()
        );
    }
}
