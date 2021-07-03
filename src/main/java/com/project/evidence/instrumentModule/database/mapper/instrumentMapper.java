package com.project.evidence.instrumentModule.database.mapper;

import com.project.evidence.instrumentModule.database.entity.instrument;

import java.util.List;

public interface instrumentMapper {
    int deleteByPrimaryKey(Integer yid);

    int insert(instrument record);

    int insertSelective(instrument record);

    instrument selectByPrimaryKey(Integer yid);

    int updateByPrimaryKeySelective(instrument record);

    int updateByPrimaryKey(instrument record);

    List<instrument> selectByJid(int jid);

    Integer maxId();
}