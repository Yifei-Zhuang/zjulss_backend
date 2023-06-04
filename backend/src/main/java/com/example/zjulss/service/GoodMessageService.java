package com.example.zjulss.service;

import com.example.zjulss.dao.GoodMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class GoodMessageService {
    @Autowired
    GoodMessageMapper goodMessageMapper;

    public boolean insertGoodMessage(int qid, String content, int uid) {
        return goodMessageMapper.insertGoodMessage(qid, content, uid, LocalDateTime.from(new Date().toInstant())) > 0;
    }
}
