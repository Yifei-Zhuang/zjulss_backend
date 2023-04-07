package com.example.zjulss.dao;

import com.example.zjulss.entity.PhoneCode;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PhoneCodeMapper {
    //insert
    @Insert({
            "insert into phone_code(phone,code) values(#{phoneNumber},#{code})"
    })
    int insertPhoneCode(String phoneNumber, String code);

    //update
    @Update({
            "update phone_code set code=#{code} where phone=#{phoneNumber}"
    })
    int updateByPhone(String phoneNumber, String code);

    //select
    @Select({
            "select * from phone_code where phone = #{phoneNumber}"
    })
    PhoneCode selectByPhone(String phoneNumber);


}
