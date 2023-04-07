package com.example.zjulss.dao;

import com.example.zjulss.entity.GoodWanted;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodWantedMapper {
    @Insert({
            "insert into good_wanted(modify,name,remark,price, sort,count,display,transaction,uid,image)",
            "values(#{modify},#{name},#{remark},#{price},#{sort},#{count},#{display},#{transaction}," +
                    "#{uid},#{image})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertGood(GoodWanted goodWanted);

    @Select({
            "select * from good_wanted where uid = #{uid} and display = 1"
    })
    List<GoodWanted> getUserWanted(int uid);
    
    @Update({
            "update good_wanted set display = 0 where id = #{id}"
    })
    int removeWanted(int id);

    //update
    @Update({
            "update good_wanted set name = #{name} where id = #{id} and display = 1"
    })
    int updateName(String name, int id);

    //update
    @Update({
            "update good_wanted set price = #{price} where id = #{id} and display = 1"
    })
    int updatePrice(Double price, int id);

    //update
    @Update({
            "update good_wanted set count = #{count} where id = #{id} and display = 1"
    })
    int updateCount(int count, int id);
}
