package com.example.zjulss.service;
import com.example.zjulss.entity.GoodForSale;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.List;
public interface SearchService {
    /**
     * 索引目标商品
     * @param id
     */
    void index(int id);
    /**
     * 移除商品索引
     * @param id
     */
    void remove(int id);
    /**
     * 根据商品名索引商品
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    List<GoodForSale> findByName(String name) throws JsonMappingException, JsonProcessingException;
     /**
     * 根据id索引商品
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    List<GoodForSale> findById(int id) throws JsonMappingException, JsonProcessingException;
}
