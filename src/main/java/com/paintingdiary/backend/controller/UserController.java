package com.paintingdiary.backend.controller;

import com.paintingdiary.backend.annotation.AuthResult;
import com.paintingdiary.backend.model.dto.BaseResponse;
import com.paintingdiary.backend.model.dto.UserDTO;
import com.paintingdiary.backend.model.entity.User;
import com.paintingdiary.backend.service.CharacterService;
import com.paintingdiary.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final CharacterService characterService;

    @GetMapping
    public BaseResponse me(@AuthResult User user) {
//        boolean hasCharacter = characterService.hasCharacter(user);

        return BaseResponse.of(UserDTO.of(user, false));
    }

}
