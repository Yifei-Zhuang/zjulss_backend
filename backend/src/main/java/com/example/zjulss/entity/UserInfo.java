package com.example.zjulss.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class UserInfo {

    private int id;
    private LocalDateTime modify;
    private String userName;
    private String phone;
    private String realName;
    private String clazz;
    private String sno;
    private String dormitory;
    private String gender;
    private LocalDateTime createTime;
    private String avatar;

}
