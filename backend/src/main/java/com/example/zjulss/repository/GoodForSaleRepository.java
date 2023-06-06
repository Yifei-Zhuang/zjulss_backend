package com.example.zjulss.repository;

import com.example.zjulss.entity.GoodForSale;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodForSaleRepository extends ElasticsearchRepository<GoodForSale,String> {
}
