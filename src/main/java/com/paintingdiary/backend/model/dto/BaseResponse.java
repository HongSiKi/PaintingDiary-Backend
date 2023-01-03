package com.paintingdiary.backend.model.dto;

import com.paintingdiary.backend.exception.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseResponse {
    private final Object result;
    private final int status;
    private final String errorDescription;

    private BaseResponse(Object result, int status, String errorDescription) {
        this.result = result;
        this.status = status;
        this.errorDescription = errorDescription;
    }

    public static BaseResponse of() {
        return BaseResponse.of(null, HttpStatus.OK);
    }

    public static BaseResponse of(Object result) {
        return BaseResponse.of(result, HttpStatus.OK);
    }

    public static BaseResponse of(Object result, HttpStatus status) {
        return BaseResponse.of(result, status, null);
    }

    public static BaseResponse exception(BaseException baseException) {
        return BaseResponse.of(null, baseException.getStatus(), baseException.getErrorDescription());
    }

    public static BaseResponse of(Object result, HttpStatus status, String errorDescription) {
        return new BaseResponse(result, status.value(), errorDescription);
    }
}
