package com.example.zjulss.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    int id;
    int seller;
    int buyer;
    int qid;
    int quantity;
    int state;
    LocalDateTime createTime;
    LocalDateTime endTime;
}
