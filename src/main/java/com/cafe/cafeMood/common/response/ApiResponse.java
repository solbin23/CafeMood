package com.cafe.cafeMood.common.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T>{

    private final String code;
    private final String message;
    private final T data;

   public static <T> ApiResponse<T> success(ResponseCode code,T data) {
       return ApiResponse.<T>builder()
               .code(code.code())
               .message(code.message())
               .data(data)
               .build();
   }

   public static<T> ApiResponse<T> fail(String code, String message) {

       return ApiResponse.<T>builder()
               .code(code)
               .message(message)
               .build();
   }
}
