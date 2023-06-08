package com.example.zjulss.mapperTest;


import com.example.zjulss.ZjulssApplication;
import com.example.zjulss.dao.PhoneCodeMapper;
import com.example.zjulss.dao.UserInfoMapper;
import com.example.zjulss.entity.PhoneCode;
import com.example.zjulss.entity.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = ZjulssApplication.class)
public class MapperTests {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private PhoneCodeMapper phoneCodeMapper;

    @Test
    public void testSelectUserInfoByPhone() {
        UserInfo userInfo = userInfoMapper.selectById(1);
        System.out.println(userInfo);

        userInfo = userInfoMapper.selectByPhone("12345678901");
        System.out.println(userInfo);
    }

    @Test
    public void testInsertUser() {
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUserName("tester");
//        userInfo.setPhone("12345678901");
//        userInfo.setRealName("realName");
//        userInfo.setClazz("clazz");
//        userInfo.setSno("sno");
//        userInfo.setDormitory("123");
//        userInfo.setGender("1");
//        userInfo.setCreateTime(LocalDateTime.now());
//        userInfo.setAvatar("baidu.com");
//
//        int rows = userInfoMapper.insertUserInfo(userInfo);
//        System.out.println(rows);
//        System.out.println(userInfo.getId());
    }

    @Test
    public void testSelectPhoneCode() {
        PhoneCode phoneCode = phoneCodeMapper.selectByPhone("123");
        System.out.println(phoneCode);
    }

    @Test
    public void testInsertPhoneCode() {
        System.out.println(phoneCodeMapper.insertPhoneCode("123", "123"));
        ;
    }

    @Test
    public void testUpdatePhoneCode() {
        System.out.println(phoneCodeMapper.updateByPhone("123", "234"));

    }

}
