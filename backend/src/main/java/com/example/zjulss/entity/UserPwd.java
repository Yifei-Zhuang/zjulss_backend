package com.example.zjulss.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserPwd {

    private int id;
    private LocalDateTime modify;
    private String password;
    private int uid;
}
