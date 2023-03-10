package com.paintingdiary.backend.model.dto.character;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class CharacterCreateRequestDTO {
    private @NotBlank String nickname;
    private String link;
    private List<@Valid SkillCreateRequestDTO> skillList = Collections.emptyList();
}
