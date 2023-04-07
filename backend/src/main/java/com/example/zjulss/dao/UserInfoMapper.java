package com.example.zjulss.dao;

import com.example.zjulss.entity.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper {

    @Select({
            "select id,user_name,modify,phone,real_name,clazz,sno,dormitory,gender,create_time,avatar ",
            "from user_info where id = #{id}"
    })
    UserInfo selectById(int id);

    @Select({
            "select id,user_name,modify,phone,real_name,clazz,sno,dormitory,gender,create_time,avatar ",
            "from user_info where phone = #{phoneNumber}"
    })
    UserInfo selectByPhone(String phoneNumber);

    @Insert({
            "insert into user_info(user_name,modify,phone,real_name,clazz,sno,dormitory,gender,create_time,avatar) ",
            "values(#{userName},#{modify},#{phone},#{realName},#{clazz},#{sno},#{dormitory},#{gender},#{createTime}," +
                    "#{avatar})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUserInfo(UserInfo userInfo);

}
