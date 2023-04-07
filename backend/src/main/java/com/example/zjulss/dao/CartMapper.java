package com.example.zjulss.dao;

import com.example.zjulss.entity.Cart;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CartMapper {
    @Update({
            "update cart set quantity = #{quantity} + 1 where uid = #{uid} and qid = #{qid} and modify=#{modify} and " +
                    "display = 1"
    })
    int addQuantity(int uid, int qid, int quantity, LocalDateTime modify);

    @Update({
            "update cart set quantity = quantity - 1 where uid = #{uid} and qid = #{qid} and modify=#{modify} and " +
                    "display = 1"
    })
    int decrQuantity(int uid, int qid, LocalDateTime modify);

    @Update({
            "update cart set quantity = #{quantity}, modify = #{modify} where id = #{id} and display = 1"
    })
    Integer setQuantity(int id, int quantity, LocalDateTime modify);

    @Select({
            "select * from cart where id = #{id} and display = 1"
    })
    Cart getCart(int id);


    @Select({
            "select * from cart where uid = #{uid} and display = 1"
    })
    List<Cart> getGoodsInCartOfUser(int uid);

    @Insert({
            "insert into cart(modify,uid,qid,address) values (#{modify},#{uid},#{qid},#{address})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addCart(Cart cart);

//    @Update({
//            "update cart set display = 0 where uid = #{uid} and qid = #{qid} and display = 1"
//    })
//    int remove(int uid, int qid);

    @Update({
            "update cart set display = 0 where id = #{id} and display = 1"
    })
    int remove(int id);
}
