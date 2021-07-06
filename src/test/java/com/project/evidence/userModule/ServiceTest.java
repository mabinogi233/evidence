package com.project.evidence.userModule;

import com.project.evidence.userModule.database.entity.user;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceTest {

    public ServiceTest(){}

    @Autowired
    Service service;

    @Test
    @Rollback
    @Transactional
    public void deleteByPrimaryKey() {
        service.deleteByPrimaryKey(0);
        System.out.println(service.selectByPrimaryKey(0));
    }

    @Test
    @Rollback
    @Transactional
    public void insert() {
        user u = new user();
        u.setJid(2);
        u.setUsername("ceshix");
        u.setPassword("123456");
        u.setAuthority("低x");
        u.setIdentity("系统管理员x");
        u.setTruename("测试x");
        u.setIdcardnumber("130104198312041235");
        service.insert(u);
        System.out.println(service.selectByIdcardNumber("130104198312041235").getUid());
    }

    @Test
    public void selectByPrimaryKey() {
        System.out.println(service.selectByPrimaryKey(0).getUsername());
    }

    @Test
    @Rollback
    @Transactional
    public void updateByPrimaryKey() {
        user u = service.selectByPrimaryKey(0);
        u.setTruename("更新测试");
        service.updateByPrimaryKey(u);
        System.out.println(service.selectByPrimaryKey(0).getTruename());
    }

    @Test
    public void getAll() {
        for(user u:service.getAll()){
            System.out.println(u.getUid());
        }
    }

    @Test
    public void selectByUserName() {
        System.out.println(service.selectByUserName("ceshi").getTruename());
    }

    @Test
    public void selectByIdcardNumber() {
        System.out.println(service.selectByIdcardNumber("130104198312041234").getTruename());
    }

    @Test
    public void selectByJid() {
        for(user u:service.selectByJid(2)){
            System.out.println(u.getUid());
        }
    }
}