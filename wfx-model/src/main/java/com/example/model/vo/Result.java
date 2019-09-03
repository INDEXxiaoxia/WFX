package com.example.model.vo;

import lombok.Data;

@Data
public class Result {
    private boolean success;//记录成功还是失败
    private String message;//记录当前操作的结果消息

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
