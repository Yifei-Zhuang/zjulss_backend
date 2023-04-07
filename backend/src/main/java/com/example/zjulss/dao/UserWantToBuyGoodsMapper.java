package com.example.zjulss.dao;

import com.example.zjulss.entity.UserWantToBuyGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserWantToBuyGoodsMapper {
    @Select({
            "select * from user_want_to_buy_goods where uid = #{uid} and display = 1"
    })
    List<UserWantToBuyGoods> getByUserId(int uid);

    @Select({
            "insert into user_want_to_buy_goods(modify,uid,qid) values(#{modify},#{uid},#{qid}"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int add(UserWantToBuyGoods userWantToBuyGoods);

    @Update({
            "update user_want_to_buy_goods set display = 0 where uid = #{uid} and qid = #{qid}"
    })
    int remove(int uid, int qid);

}
