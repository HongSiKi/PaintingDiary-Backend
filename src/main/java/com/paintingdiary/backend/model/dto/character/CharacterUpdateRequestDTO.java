package com.paintingdiary.backend.model.dto.character;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class CharacterUpdateRequestDTO {
    private String nickname;
    private String description;
    private String link;
    private List<@Valid SkillUpdateRequestDTO> skillList = Collections.emptyList();
}
