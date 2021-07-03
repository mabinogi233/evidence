package com.project.evidence.evidenceModule.database.mapper;

import com.project.evidence.evidenceModule.database.entity.evidence;

import java.util.List;

public interface evidenceMapper {
    int deleteByPrimaryKey(Integer wid);

    int insert(evidence record);

    int insertSelective(evidence record);

    evidence selectByPrimaryKey(Integer wid);

    int updateByPrimaryKeySelective(evidence record);

    int updateByPrimaryKey(evidence record);

    Integer maxId();

    List<evidence> selectByUid(Integer uid);

    List<evidence> selectByText(String wtext);

    List<evidence> getAll();
}