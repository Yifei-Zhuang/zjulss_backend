package com.example.zjulss.response;

import com.example.zjulss.handle.GlobalExceptionHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wsk1103
 * @date 2019/5/8
 * @description 描述
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
    private int result;

    private String msg;

    public BaseResponse(int result) {
        this.result = result;
    }

    public static BaseResponse fail() {
        return new BaseResponse(0, GlobalExceptionHandler.DEFAULT_ERROR_MESSAGE);
    }

    public static BaseResponse fail(String msg) {
        return new BaseResponse(0, msg);
    }

    public static BaseResponse fail(int code, String msg) {
        return new BaseResponse(code, msg);
    }


    public static BaseResponse fail(int result) {
        return new BaseResponse(result);
    }

    public static BaseResponse success() {
        return new BaseResponse(1, "success");
    }

    public static BaseResponse success(String msg) {
        return new BaseResponse(1, msg);
    }
}
