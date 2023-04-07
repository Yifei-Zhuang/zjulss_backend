package com.example.zjulss.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Cart {

    private int id;
    private LocalDateTime modify;
    private int display;
    private int uid;
    private int qid;
    private int quantity;
    private String address;

}
