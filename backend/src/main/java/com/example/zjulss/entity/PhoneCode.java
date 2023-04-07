package com.example.zjulss.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneCode {
    public int id;
    public String phoneNumber;
    public String code;
}
