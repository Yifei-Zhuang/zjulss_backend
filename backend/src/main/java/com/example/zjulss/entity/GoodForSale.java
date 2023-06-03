package com.example.zjulss.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@Document(indexName = "goods", type = "_doc")
public class GoodForSale {
    @Id
    private int id;
    @Field(type = FieldType.Text)
    private LocalDateTime modify;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String name;
    @Field(type = FieldType.Integer)
    private int level;
    @Field(type = FieldType.Text)
    private String remark;
    @Field(type = FieldType.Double)
    private Double price;
    @Field(type = FieldType.Integer)
    private int sort;
    @Field(type = FieldType.Integer)
    private int count;
    @Field(type = FieldType.Integer)
    private int display;
    @Field(type = FieldType.Integer)
    private int transaction;
    @Field(type = FieldType.Integer)
    private int sales;
    @Field(type = FieldType.Integer)
    private int uid;
    @Field(type = FieldType.Text)
    private String image;
//    @Id
    private String idForElastic;

}