package com.paintingdiary.backend.model.dto.character;

import com.paintingdiary.backend.annotation.NullOrNotBlank;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class CharacterUpdateRequestDTO {
    private @NullOrNotBlank String nickname;
    private @NullOrNotBlank String description;
    private @NullOrNotBlank String link;
    private List<@Valid SkillUpdateRequestDTO> skillList = Collections.emptyList();
}
