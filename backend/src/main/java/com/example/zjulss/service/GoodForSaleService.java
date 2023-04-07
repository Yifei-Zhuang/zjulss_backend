package com.example.zjulss.service;

import com.example.zjulss.dao.GoodForSaleMapper;
import com.example.zjulss.entity.GoodForSale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodForSaleService {
    @Autowired
    GoodForSaleMapper goodForSaleMapper;

    public GoodForSale getGoodInfo(int qid) {
        return goodForSaleMapper.getGood(qid);
    }

    public boolean insertGood(GoodForSale goodForSale) {
        return goodForSaleMapper.insertGood(goodForSale) > 0;
    }

    public boolean removeGood(int id) {
        return goodForSaleMapper.deleteGood(id) > 0;
    }

}