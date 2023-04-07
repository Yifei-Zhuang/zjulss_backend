package com.example.zjulss.dao;

import com.example.zjulss.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
    @Select({
            "select * from orders where seller_id = #{uid} or buyer_id = #{uid}"
    })
    public List<Order> getOrderOfUser(int uid);

    @Select({
            "select * from orders where seller = #{uid}"
    })
    public List<Order> getSells(int uid);

    @Select({
            "select * from orders where buyer = #{uid}"
    })
    public List<Order> getBroughts(int uid);

    @Insert({
            "insert into orders(seller,buyer,qid,quantity,state,create_time) values(#{seller},#{buyer}," +
                    "#{qid},#{quantity},#{state},#{createTime})"
    })
    public int addOrder(Order order);


    @Update({
            "update orders set state = 1,end_time = #{localDateTime} where id = #{id} and state = 0"
    })
    public int acceptOrder(int id, LocalDateTime localDateTime);

    @Update({
            "update orders set state = 2,end_time = #{localDateTime} where id = #{id} and state = 0"
    })
    public int refuseOrder(int id, LocalDateTime localDateTime);

    @Update({
            "update orders set state = 3,end_time = #{localDateTime} where id = #{id} and state = 0"
    })
    public int cancelOrder(int id, LocalDateTime localDateTime);

    @Select({
            "select * from orders where id = #{id}"
    })
    public Order getOrder(int id);
}
