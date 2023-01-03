package com.paintingdiary.backend.controller;

import com.paintingdiary.backend.annotation.AuthResult;
import com.paintingdiary.backend.model.dto.BaseResponse;
import com.paintingdiary.backend.model.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    @GetMapping
    public BaseResponse me(@AuthResult UserDTO userDTO) {
        return BaseResponse.of(userDTO);
    }
}
