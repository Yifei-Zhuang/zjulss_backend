package com.example.zjulss.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class OrderVO {
    int id;
    @NotNull
    String sellId;
    @NotNull
    String buyerId;
    @NotNull
    String qid;
    @NotNull
    String quantity;
}
