package com.example.zjulss.dao;

import com.example.zjulss.entity.UserInfo;
import org.apache.ibatis.annotations.*;

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

    @Update({
            "update user_info set real_name = #{realName} where id = #{id}"
    })
    int updateRealName(UserInfo userInfo);

    @Update({
            "update user_info set clazz = #{clazz} where id = #{id}"
    })
    int updateClazz(UserInfo userInfo);

    @Update({
            "update user_info set sno = #{sno} where id = #{id}"
    })
    int updateSno(UserInfo userInfo);

    @Update({
            "update user_info set dormitory = #{dormitory} where id = #{id}"
    })
    int updateDormitory(UserInfo userInfo);

    @Update({
            "update user_info set gender = #{gender} where id = #{id}"
    })
    int updateGender(UserInfo userInfo);

    @Update({
            "update user_info set avatar = #{avatar} where id = #{id}"
    })
    int updateAvatar(UserInfo userInfo);
}
