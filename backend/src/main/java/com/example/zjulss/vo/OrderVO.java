package com.example.zjulss.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO {
    int id;
    @NotNull
    @NotBlank
    String sellId;
    @NotNull
    @NotBlank
    String buyerId;
    @NotNull
    @NotBlank
    String qid;
    @NotNull
    @NotBlank
    String quantity;
}
