package com.project.evidence.organizationModule.database.mapper;

import com.project.evidence.organizationModule.database.entity.organization;

import java.util.List;

public interface organizationMapper {
    int deleteByPrimaryKey(Integer jid);

    int insert(organization record);

    int insertSelective(organization record);

    organization selectByPrimaryKey(Integer jid);

    int updateByPrimaryKeySelective(organization record);

    int updateByPrimaryKey(organization record);

    List<organization> getAll();

    Integer maxId();

    List<organization> selectByJName(String JName);
}