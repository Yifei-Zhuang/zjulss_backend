package com.example.zjulss.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GoodForSale {

    private int id;
    private LocalDateTime modify;
    private String name;
    private int level;
    private String remark;
    private Double price;
    private int sort;
    private int count;
    private int display;
    private int transaction;
    private int sales;
    private int uid;
    private String image;
    private String idForElastic;

}