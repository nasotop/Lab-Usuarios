package com.lab.usuarios.lab_usuarios.domain.dataTransferObject;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.AccessLevel;
@Getter
@Builder(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultDto<T> {
    private final String message;
    private final boolean success;
    private final T data;

     public static <T> ResultDto<T> ok(T data) {
        return ResultDto.<T>builder()
                .success(true)
                .data(data)
                .build();
    }

    public static <T> ResultDto<T> fail(String message) {
        return ResultDto.<T>builder()
                .success(false)
                .message(message)
                .build();
    }
    public String getErrorMessage() {
        return message;
    }
}
