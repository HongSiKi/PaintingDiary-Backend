package com.paintingdiary.backend.controller;

import com.paintingdiary.backend.model.dto.AuthDTO;
import com.paintingdiary.backend.model.dto.BaseResponse;
import com.paintingdiary.backend.model.dto.UserDTO;
import com.paintingdiary.backend.model.entity.User;
import com.paintingdiary.backend.service.UserService;
import com.paintingdiary.backend.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/join")
    public BaseResponse join(@RequestParam String email, @RequestParam String password, @RequestParam String nickname) {

        AuthDTO authDTO = authService.joinAuth(email, password, nickname);
        User user = userService.syncUserWithDuplicateError(authDTO.uid(), authDTO.nickname());

        return BaseResponse.of(UserDTO.builder()
                .uid(user.getUid())
                .nickname(user.getNickname())
                .type(user.getType())
                .build());
    }


}
