package com.paintingdiary.backend.model.dto;

import com.paintingdiary.backend.model.enums.UserType;
import lombok.Builder;

@Builder
public record UserDTO(String uid, String nickname, UserType type) {
}
