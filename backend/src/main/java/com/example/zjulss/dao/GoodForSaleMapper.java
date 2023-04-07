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
            "select * from good where id = #{id}"
    })
    GoodForSale getGood(int id);

    @Delete({
            "delete from good where id = #{id}"
    })
    int deleteGood(int id);
}
