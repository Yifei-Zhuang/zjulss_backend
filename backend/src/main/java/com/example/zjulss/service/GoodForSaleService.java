package com.example.zjulss.service;

import com.example.zjulss.dao.GoodForSaleMapper;
import com.example.zjulss.entity.GoodForSale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoodForSaleService {
    @Autowired
    GoodForSaleMapper goodForSaleMapper;

    @Autowired
    GoodESService goodESService;


    public GoodForSale getGoodInfo(int qid) {
        return goodForSaleMapper.getGood(qid);
    }

    public List<GoodForSale> getUserSells(int uid){
        return goodForSaleMapper.getUserSells(uid);
    }

    public boolean insertGood(GoodForSale goodForSale) {
        if(goodForSaleMapper.insertGood(goodForSale) > 0){
            goodESService.insertGoodForSale(goodForSale);
            return true;
        }
        return false;
    }

    public boolean removeGood(int id) {
        goodESService.deleteGoodForSaleById(id);
        return goodForSaleMapper.deleteGood(id) > 0;
    }

    public boolean updateGoodName(int id, String newName) {
        GoodForSale good = getGoodInfo(id);
        good.setName(newName);
        goodESService.updateGoodForSale(good);
        return goodForSaleMapper.updateName(newName, id) == 1;
    }

    public boolean updateGoodPrice(int id, Double price) {
        GoodForSale good = getGoodInfo(id);
        good.setPrice(price);
        goodESService.updateGoodForSale(good);
        return goodForSaleMapper.updatePrice(price, id) == 1;
    }

    public boolean updateGoodCount(int id, int count) {
        GoodForSale good = getGoodInfo(id);
        good.setCount(count);
        goodESService.updateGoodForSale(good);
        return goodForSaleMapper.updateCount(count, id) == 1;
    }

    public List<GoodForSale> getGoodFeed(){
        return new ArrayList<>(goodForSaleMapper.getTenLatestGoods());
    }

    public List<GoodForSale> getGoodsByOffsetAndLimit(int offset,int limit){
        return new ArrayList<>(goodForSaleMapper.getGoodsByOffsetAndLimit(offset,limit));
    }

    public List<GoodForSale> getGoodsBySort(int sort){
        return new ArrayList<>(goodForSaleMapper.getGoodsBySort(sort));
    }
}
