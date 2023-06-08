package com.example.zjulss.dao;

import com.example.zjulss.entity.GoodMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper
public interface GoodMessageMapper {
    @Insert({
            "insert into good_message(modify,qid,content,uid) values(#{modify},#{qid},#{content},#{uid})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertGoodMessage(GoodMessage goodMessage);
}
