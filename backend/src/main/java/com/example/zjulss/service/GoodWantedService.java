package com.example.zjulss.service;

import com.example.zjulss.dao.GoodWantedMapper;
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


}
