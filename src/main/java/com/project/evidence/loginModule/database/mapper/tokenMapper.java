package com.project.evidence.loginModule.database.mapper;

import com.project.evidence.loginModule.database.entity.token;

public interface tokenMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(token record);

    int insertSelective(token record);

    token selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(token record);

    int updateByPrimaryKey(token record);

    token selectByToken(String token);

    token selectByRefToken(String reftoken);
}