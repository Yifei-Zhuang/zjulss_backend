package com.example.zjulss.service;

import com.example.zjulss.dao.UserPublishGoodMapper;
import com.example.zjulss.entity.UserPublishGood;
import com.example.zjulss.vo.UserPublishGoodVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPublishGoodService {
    @Autowired
    UserPublishGoodMapper userPublishGoodMapper;

    public boolean addPublish(UserPublishGoodVO userPublishGoodVO) {
        return userPublishGoodMapper.addRecord(userPublishGoodVO) == 1;
    }

    public boolean removePublish(int id) {
        return userPublishGoodMapper.remove(id) == 1;
    }

    public List<UserPublishGood> getUserPublishs(int uid) {
        return userPublishGoodMapper.getRecordsByUid(uid);
    }

}
