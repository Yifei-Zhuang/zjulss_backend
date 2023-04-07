package com.example.zjulss.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CartVO {
    private LocalDateTime modify;
    private int uid;
    private int qid;
    private int quantity;
    private String address;
}
