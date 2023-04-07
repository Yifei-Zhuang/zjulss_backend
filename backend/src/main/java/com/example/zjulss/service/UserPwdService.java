package com.example.zjulss.service;

import com.example.zjulss.dao.UserPwdMapper;
import com.example.zjulss.entity.UserInfo;
import com.example.zjulss.entity.UserPwd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserPwdService {
    @Autowired
    UserPwdMapper userPwdMapper;

    @Autowired
    UserInfoService userInfoService;

    public boolean saveUserPwd(UserPwd userPwd) {
        userPwd.setPassword(DigestUtils.md5DigestAsHex((userPwd.getPassword()).getBytes()));
        return userPwdMapper.insertUserPwd(userPwd) == 1;
    }

    public UserPwd getUserPwdByUid(int uid) {
        return userPwdMapper.selectByUId(uid);
    }

    public UserPwd getUserPwdById(int id) {
        return userPwdMapper.selectById(id);
    }


    public boolean updateUserPwd(UserPwd userPwd) {
        userPwd.setPassword(DigestUtils.md5DigestAsHex((userPwd.getPassword()).getBytes()));
        return userPwdMapper.updatePwd(userPwd.getUid(), userPwd.getPassword()) == 1;
    }

    public boolean login(String phoneNumber, String password) {
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phoneNumber);
        if (userInfo == null) {
            return false;
        }
        UserPwd userPwd = getUserPwdByUid(userInfo.getId());
        if (userPwd == null) {
            return false;
        }
        return DigestUtils.md5DigestAsHex(password.getBytes()).equals(userPwd.getPassword());
    }
}
