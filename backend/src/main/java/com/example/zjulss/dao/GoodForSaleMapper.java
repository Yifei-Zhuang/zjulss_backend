package com.example.zjulss.dao;

import com.example.zjulss.entity.GoodForSale;
import org.apache.ibatis.annotations.*;

@Mapper
public interface GoodForSaleMapper {
    @Insert({
            "insert into good(modify,name,level,remark,price, sort,count,transaction,sales,uid,image)",
            "values(#{modify},#{name},#{level},#{remark},#{price},#{sort},#{count},#{transaction}," +
                    "#{sales},#{uid},#{image})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertGood(GoodForSale goodForSale);

    //update
    @Select({
            "select * from good where id = #{id} and display = 1"
    })
    GoodForSale getGood(int id);

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
}
