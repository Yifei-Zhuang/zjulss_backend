package com.example.zjulss.service;

import com.example.zjulss.dao.UserInfoMapper;
import com.example.zjulss.dao.UserPwdMapper;
import com.example.zjulss.entity.UserInfo;
import com.example.zjulss.entity.UserPwd;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;
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

    public boolean updateRealName(UserInfo userInfo){
        return userInfoMapper.updateRealName(userInfo) != 0;
    }

    public boolean updateClazz(UserInfo userInfo){
        return userInfoMapper.updateClazz(userInfo) != 0;
    }

    public boolean updateSno(UserInfo userInfo){
        return userInfoMapper.updateSno(userInfo) != 0;
    }

    public boolean updateDormitory(UserInfo userInfo){
        return userInfoMapper.updateDormitory(userInfo) != 0;
    }

    public boolean updateGender(UserInfo userInfo){
        return userInfoMapper.updateGender(userInfo) != 0;
    }

    public boolean updateAvatar(UserInfo userInfo){
        return userInfoMapper.updateAvatar(userInfo) != 0;
    }

}
