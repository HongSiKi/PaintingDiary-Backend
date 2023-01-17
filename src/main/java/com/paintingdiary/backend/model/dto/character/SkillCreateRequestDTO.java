package com.paintingdiary.backend.model.dto.character;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record SkillCreateRequestDTO(
        @NotBlank String title,
        @NotBlank String description,
        @Min(0) @Max(5) int progress
) {

}
