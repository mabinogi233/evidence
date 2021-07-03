package com.project.evidence.userModule.database.mapper;

import com.project.evidence.userModule.database.entity.user;

import java.util.List;

public interface userMapper {
    int deleteByPrimaryKey(Integer uid);

    int insert(user record);

    int insertSelective(user record);

    user selectByPrimaryKey(Integer uid);

    int updateByPrimaryKeySelective(user record);

    int updateByPrimaryKey(user record);

    user selectByUserName(String userName);

    user selectByIdCardNumber(String idCardNumber);

    List<user> getAll();

    Integer maxId();

    List<user> selectByJid(int jid);
}