package com.example.zjulss.service;

import com.example.zjulss.dao.UserInfoMapper;
import com.example.zjulss.dao.UserPwdMapper;
import com.example.zjulss.entity.UserInfo;
import com.example.zjulss.entity.UserPwd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;

    public boolean saveUserInfo(UserInfo userInfo) {
        return userInfoMapper.insertUserInfo(userInfo) == 1;
    }

    public UserInfo getUserInfoByPhone(String phoneNumber) {
        return userInfoMapper.selectByPhone(phoneNumber);
    }

    public UserInfo getUserInfoById(int id) {
        return userInfoMapper.selectById(id);
    }

}
