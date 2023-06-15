package com.example.zjulss.service;

import com.example.zjulss.dao.GoodWantedMapper;
import com.example.zjulss.entity.GoodForSale;
import com.example.zjulss.entity.GoodWanted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodWantedService {
    @Autowired
    GoodWantedMapper goodWantedMapper;

    public boolean addGoodWanted(GoodWanted goodWanted) {
        return goodWantedMapper.insertGood(goodWanted) == 1;
    }

    public List<GoodWanted> getUserWanted(int uid) {
        return goodWantedMapper.getUserWanted(uid);
    }

    public boolean updateWantedName(int id, String newName) {
        return goodWantedMapper.updateName(newName, id) == 1;
    }

    public boolean updateWantedPrice(int id, Double price) {
        return goodWantedMapper.updatePrice(price, id) == 1;
    }

    public boolean updateWantedCount(int id, int count) {
        return goodWantedMapper.updateCount(count, id) == 1;
    }

    public boolean removeWanted(int id) {
        return goodWantedMapper.removeWanted(id) == 1;
    }
    public boolean updateGoodSort(int id, int sort) {
        return goodWantedMapper.updateSort(sort, id) == 1;
    }

    public boolean updateGoodRemark(int id, String remark) {
        return goodWantedMapper.updateRemark(remark, id) == 1;
    }

    public boolean updateGoodTransaction(int id, int transaction) {
        return goodWantedMapper.updateTransaction(transaction, id) == 1;
    }

    public boolean updateGoodImage(int id, String image) {
        return goodWantedMapper.updateImage(image, id) == 1;
    }

}
