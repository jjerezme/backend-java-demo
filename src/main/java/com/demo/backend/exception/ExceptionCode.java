package com.demo.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    RUNTIME("RUNTIME"),
    REQUEST("REQUEST"),
    BUSINESS("BUSINESS"),
    DATA("DATA");

    private String code;
}