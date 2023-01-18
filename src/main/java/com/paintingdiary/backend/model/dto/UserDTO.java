package com.paintingdiary.backend.model.dto;

import com.paintingdiary.backend.model.entity.User;
import com.paintingdiary.backend.model.enums.UserType;
import lombok.Builder;

@Builder
public record UserDTO(String uid, String nickname, UserType type, boolean hasCharacter) {
    public static UserDTO of(User user, boolean hasCharacter) {
        if (user == null) {
            return null;
        }

        return new UserDTO(user.getUid(), user.getNickname(), user.getType(), hasCharacter);
    }
}
