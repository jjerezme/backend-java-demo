package com.demo.backend.dto;

import com.demo.backend.constants.ResponseStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDTO {
    public String status;
    public Object result;

    public static ResponseDTO error(ErrorDTO error) {
        return new ResponseDTO(ResponseStatus.ERROR, error);
    }

    public static ResponseDTO success() {
        return new ResponseDTO(ResponseStatus.SUCCESS, "OK");
    }

    public static ResponseDTO success(Object result) {
        return new ResponseDTO(ResponseStatus.SUCCESS, result);
    }
}
