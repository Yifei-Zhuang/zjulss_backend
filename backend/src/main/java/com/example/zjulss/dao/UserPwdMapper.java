package com.example.zjulss.dao;

import com.example.zjulss.entity.UserPwd;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserPwdMapper {

    @Select({
            "select id,modify,password,uid",
            "from user_pwd where id = #{id}"
    })
    UserPwd selectById(int id);

    @Select({
            "select id,modify,password,uid",
            "from user_pwd where uid = #{uid}"
    })
    UserPwd selectByUId(int uid);

    @Insert({
            "insert into user_pwd(uid,password) ",
            "values(#{uid},#{password})"
    })
    int insertUserPwd(UserPwd userPwd);

    @Update({
            "update user_pwd set password=#{newPassword} where uid=#{uid}"
    })
    int updatePwd(int uid, String newPassword);

}
