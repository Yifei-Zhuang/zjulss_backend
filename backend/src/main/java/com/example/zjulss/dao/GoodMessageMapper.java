package com.example.zjulss.dao;

import com.example.zjulss.entity.GoodMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface GoodMessageMapper {
    @Insert({
            "insert into good_message(modify,qid,content,uid) values(#{modify},#{qid},#{content},#{uid})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertGoodMessage(GoodMessage goodMessage);

    @Select({
            "select * from good_message where qid = #{qid}"
    })
    List<GoodMessage> getGoodMessageByQid(int qid);


}
