package com.example.zjulss.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodWantedVO {
    @NotBlank
    @NotNull
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
