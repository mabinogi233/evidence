package com.project.evidence.identifyModule.database.mapper;

import com.project.evidence.identifyModule.database.entity.identify;

import java.util.List;

public interface identifyMapper {
    int deleteByPrimaryKey(Integer bid);

    int insert(identify record);

    int insertSelective(identify record);

    identify selectByPrimaryKey(Integer bid);

    int updateByPrimaryKeySelective(identify record);

    int updateByPrimaryKey(identify record);

    List<identify> selectByWid(Integer wid);

    List<identify> selectByUid(Integer uid);

    List<identify> selectByQid(Integer qid);

    Integer maxId();

}