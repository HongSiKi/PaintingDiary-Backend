package com.paintingdiary.backend.controller;

import com.paintingdiary.backend.annotation.AuthResult;
import com.paintingdiary.backend.model.dto.BaseResponse;
import com.paintingdiary.backend.model.dto.UserDTO;
import com.paintingdiary.backend.model.entity.User;
import com.paintingdiary.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/api/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping
    public BaseResponse me(@AuthResult User user) {
        return BaseResponse.of(UserDTO.of(user));
    }


    @GetMapping("/nickname")
    public BaseResponse isDuplicatedNickname(@RequestParam String nickname) {
        boolean isDuplicated = userService.isDuplicatedNickname(nickname);

        return BaseResponse.of(Map.of("duplicated", isDuplicated));
    }
}
