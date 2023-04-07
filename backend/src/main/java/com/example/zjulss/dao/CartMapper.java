package com.example.zjulss.dao;

import com.example.zjulss.entity.Cart;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CartMapper {
    @Update({
            "update cart set quantity = #{quantity} + 1 where uid = #{uid} and qid = #{qid} and modify=#{modify}"
    })
    int addQuantity(int uid, int qid, int quantity, LocalDateTime modify);

    @Update({
            "update cart set quantity = quantity - 1 where uid = #{uid} and qid = #{qid} and modify=#{modify}"
    })
    int decrQuantity(int uid, int qid, LocalDateTime modify);

    @Update({
            "update cart set quantity = #{quantity}, modify = #{modify} where id = #{id}"
    })
    Integer setQuantity(int id, int quantity, LocalDateTime modify);


    @Select({
            "select * from cart where uid = #{uid} and display = 1"
    })
    List<Cart> getGoodsInCartOfUser(int uid);

    @Insert({
            "insert into cart(modify,uid,qid,address) values (#{modify},#{uid},#{qid},#{address})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addCart(Cart cart);

    @Delete({
            "delete from cart where uid = #{uid} and qid = #{qid}"
    })
    int remove(int uid, int qid);


}
