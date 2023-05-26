package com.example.zjulss.dao;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.example.zjulss.entity.*;

@Repository
public interface  GoodForSaleRepository extends ElasticsearchRepository<GoodForSale,Integer> {
     List<GoodForSale> findAll();
     List<GoodForSale> findByName(String name);
     List<GoodForSale> findByNameContaining(String name);
}
