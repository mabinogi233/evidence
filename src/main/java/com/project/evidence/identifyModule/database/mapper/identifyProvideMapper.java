package com.project.evidence.identifyModule.database.mapper;

import com.project.evidence.identifyModule.database.entity.identifyProvide;

import java.util.List;

public interface identifyProvideMapper {
    int deleteByPrimaryKey(Integer qid);

    int insert(identifyProvide record);

    int insertSelective(identifyProvide record);

    identifyProvide selectByPrimaryKey(Integer qid);

    int updateByPrimaryKeySelective(identifyProvide record);

    int updateByPrimaryKey(identifyProvide record);

    List<identifyProvide> selectByJid(Integer jid);

    Integer maxId();

    List<identifyProvide> getAll();
}