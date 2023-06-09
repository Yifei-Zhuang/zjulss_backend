package com.example.zjulss.service;

import com.example.zjulss.dao.GoodMessageMapper;
import com.example.zjulss.entity.GoodMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class GoodMessageService {
    @Autowired
    GoodMessageMapper goodMessageMapper;

    public boolean insertGoodMessage(GoodMessage goodMessage) {
        goodMessage.setModify(LocalDateTime.now());
        return goodMessageMapper.insertGoodMessage(goodMessage) > 0;
    }
    public List<GoodMessage> getGoodMessageByQid(int qid){
        return goodMessageMapper.getGoodMessageByQid(qid);
    }
}
