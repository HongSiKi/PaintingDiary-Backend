package com.paintingdiary.backend.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paintingdiary.backend.model.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class SimpleLogoutSuccessHandler implements LogoutSuccessHandler {
    private final ObjectMapper objectMapper;
    private final ManageAuthService manageAuthService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        manageAuthService.clearAuth(request, response);

        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(objectMapper.writeValueAsString(BaseResponse.of()));
    }
}
