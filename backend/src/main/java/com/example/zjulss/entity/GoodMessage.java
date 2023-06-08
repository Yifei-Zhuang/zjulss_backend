package com.example.zjulss.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodMessage {
    private int id;
    private LocalDateTime modify;
    private int qid;
    private String content;
    private int display;
    private int uid;
}
