package com.example.zjulss.vo;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class User {
    int code;
//    @NotBlank
    String password;
//    @NotBlank
    String userName;
//    @NotBlank
    String phoneNumber;
    String realName;
    String clazz;
    String sno;
    String dormitory;
    String gender;
    String createTime;
    String avatar;

    // just in server
    int idInUserInfo;
    int idInUserPwd;

}
