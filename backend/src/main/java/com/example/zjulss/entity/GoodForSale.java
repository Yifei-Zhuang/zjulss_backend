package com.example.zjulss.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "goodindex")
public class GoodForSale {
    @Id
    private Integer id;

    @Field(type = FieldType.Text, name = "modify")
    private LocalDateTime modify;

    @Field(type = FieldType.Text, name = "name")
    private String name;

    @Field(type = FieldType.Integer, name = "level")
    private int level;

    @Field(type = FieldType.Text, name = "remark")
    private String remark;

    @Field(type = FieldType.Double, name = "price")
    private Double price;

    @Field(type = FieldType.Integer, name = "sort")
    private int sort;

    @Field(type = FieldType.Integer, name = "count")
    private int count;

    @Field(type = FieldType.Integer, name = "display")
    private int display;

    @Field(type = FieldType.Integer, name = "transaction")
    private int transaction;

    @Field(type = FieldType.Integer, name = "sales")
    private int sales;

    @Field(type = FieldType.Integer, name = "uid")
    private int uid;

    @Field(type = FieldType.Text, name = "image")
    private String image;

}