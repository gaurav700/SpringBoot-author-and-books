package com.homework3.Homework3.advices;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private T data;
    private ApiError error;

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(ApiError error) {
        this.error = error;
    }
}
