package com.example.zjulss.dao;

import com.example.zjulss.entity.UserPublishGood;
import com.example.zjulss.vo.UserPublishGoodVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserPublishGoodMapper {
    @Insert({
            "insert into user_publish_goods(modify,display,uid,sid,quantity) values(#{modify},#{display},#{uid}," +
                    "#{sid},#{quantity})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addRecord(UserPublishGoodVO userPublishGoodVO);

    @Select({
            "select * from user_publish_goods where uid = #{uid} and display = 1"
    })
    List<UserPublishGood> getRecordsByUid(int uid);

    @Update({
            "update user_publish_goods set display = 0 where id = #{id}"
    })
    int remove(int id);

}
