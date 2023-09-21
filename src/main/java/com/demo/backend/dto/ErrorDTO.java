package com.demo.backend.dto;

import com.demo.backend.exception.ExceptionCode;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
    private String code;
    private String message;

    private static ErrorDTO create(ExceptionCode code, String message) {
        return new ErrorDTO(code.toString(), message);
    }

    public static ErrorDTO runtime(String message) {
        return create(ExceptionCode.RUNTIME, message);
    }

    public static ErrorDTO runtime(Exception ex) {
        return runtime(ex.getMessage());
    }

    public static ErrorDTO request(String message) {
        return create(ExceptionCode.REQUEST, message);
    }

    public static ErrorDTO request(Exception ex) {
        return request(ex.getMessage());
    }

    public static ErrorDTO business(String message) {
        return create(ExceptionCode.BUSINESS, message);
    }

    public static ErrorDTO business(Exception ex) {
        return business(ex.getMessage());
    }

    public static ErrorDTO data(String message) {
        return create(ExceptionCode.DATA, message);
    }

    public static ErrorDTO data(Exception ex) {
        return data(ex.getMessage());
    }
}
