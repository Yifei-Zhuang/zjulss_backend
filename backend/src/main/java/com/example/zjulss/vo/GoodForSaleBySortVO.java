package com.example.zjulss.vo;

import com.example.zjulss.entity.GoodForSale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodForSaleBySortVO {
    List<GoodForSale> list;
    int total;
}
