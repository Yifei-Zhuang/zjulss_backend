package com.example.zjulss.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodWanted {
    private int id;
    private LocalDateTime modify;
    private String name;
    private String remark;
    private Double price;
    private int sort;
    private int count;
    private int display;
    private int transaction;
    private int uid;
    private String image;
}
