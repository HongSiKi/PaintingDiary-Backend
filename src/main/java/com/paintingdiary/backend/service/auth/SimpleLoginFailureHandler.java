package com.paintingdiary.backend.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paintingdiary.backend.exception.BaseException;
import com.paintingdiary.backend.exception.CommonException;
import com.paintingdiary.backend.model.dto.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final ObjectMapper objectMapper;
    private final ManageAuthService manageAuthService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        manageAuthService.clearAuth(request, response);

        BaseException baseException = CommonException.UNAUTHORIZED;
        if (exception.getCause() != null && exception.getCause() instanceof BaseException) {
            baseException = (BaseException) exception.getCause();
        }

        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(objectMapper.writeValueAsString(BaseResponse.exception(baseException)));
    }
}
