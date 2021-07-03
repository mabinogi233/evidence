package com.project.evidence.identifyModule.database.mapper;

import com.project.evidence.identifyModule.database.entity.identifyResult;

import java.util.List;

public interface identifyResultMapper {
    int deleteByPrimaryKey(Integer bid);

    int insert(identifyResult record);

    int insertSelective(identifyResult record);

    identifyResult selectByPrimaryKey(Integer bid);

    int updateByPrimaryKeySelective(identifyResult record);

    int updateByPrimaryKey(identifyResult record);

    List<identifyResult> selectByJid(Integer jid);

    Integer maxId();
}