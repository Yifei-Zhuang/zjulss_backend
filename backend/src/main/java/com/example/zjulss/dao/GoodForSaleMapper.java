package com.example.zjulss.dao;

import com.example.zjulss.entity.GoodForSale;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodForSaleMapper {
    @Insert({
            "insert into good(modify,name,level,remark,price, sort,count,transaction,sales,uid,image)",
            "values(#{modify},#{name},#{level},#{remark},#{price},#{sort},#{count},#{transaction}," +
                    "#{sales},#{uid},#{image})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertGood(GoodForSale goodForSale);


    @Select({
            "select * from good where uid = #{uid}"
    })
    List<GoodForSale> getUserSells(int uid);


    @Select({
            "select * from good where uid = #{uid} limit #{offset},#{limit}"
    })
    List<GoodForSale> getUserSellsByOffsetAndLimit(int uid, int offset, int limit);

    //update
    @Select({
            "select * from good where id = #{id} and display = 1"
    })
    GoodForSale getGood(int id);

    @Select({
            "select * from good where display = 1 order by id desc limit 0,10"
    })
    List<GoodForSale> getTenLatestGoods();

    @Select({
            "select * from good where display = 1 order by id desc limit #{offset},#{limit}"
    })
    List<GoodForSale> getGoodsByOffsetAndLimit(int offset,int limit);

    @Select({
            "select * from good where display = 1 and sort = #{sort} order by id desc"
    })
    List<GoodForSale> getGoodsBySort(int sort);


    @Update({
            "update good set display = 0 where id = #{id} and display = 1"
    })
    int deleteGood(int id);



    //update
    @Update({
            "update good set name = #{name} where id = #{id} and display = 1"
    })
    int updateName(String name, int id);

    //update
    @Update({
            "update good set price = #{price} where id = #{id} and display = 1"
    })
    int updatePrice(Double price, int id);

    //update
    @Update({
            "update good set count = #{count} where id = #{id} and display = 1"
    })
    int updateCount(int count, int id);

    @Update({
            "update good set sort = #{sort} where id = #{id} and display = 1"
    })
    int updateSort(int sort, int id);

    @Update({
            "update good set remark = #{remark} where id = #{id} and display = 1"
    })
    int updateRemark(String remark, int id);

    @Update({
            "update good set transaction = #{transaction} where id = #{id} and display = 1"
    })
    int updateTransaction(int transaction, int id);

    @Update({
            "update good set image = #{image} where id = #{id} and display = 1"
    })
    int updateImage(String image, int id);

    @Select({
            "select * from good where display = 1"
    })
    List<GoodForSale> getAllGoods();
}
