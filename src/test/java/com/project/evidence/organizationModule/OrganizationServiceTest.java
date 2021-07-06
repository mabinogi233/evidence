package com.project.evidence.organizationModule;

import com.project.evidence.organizationModule.database.entity.organization;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrganizationServiceTest {

    public OrganizationServiceTest(){}

    @Autowired
    Service service;

    @Test
    @Rollback
    @Transactional
    public void deleteByPrimaryKey() {
        service.deleteByPrimaryKey(1);
    }

    @Test
    @Rollback
    @Transactional
    public void insert() {
        organization o = new organization();
        o.setJid(200);
        o.setJname("测试机构B");
        o.setJtext("这是一个测试机构");
        service.insert(o);
    }

    @Test
    @Rollback
    @Transactional
    public void selectByPrimaryKey() {
        organization o = service.selectByPrimaryKey(1);
        if(o!=null){
            System.out.println(o.getJtext());
        }else {
            System.out.println("不存在");
        }
    }

    @Test
    @Rollback
    @Transactional
    public void updateByPrimaryKey() {
        organization o = new organization();
        o.setJid(200);
        o.setJname("测试机构B");
        o.setJtext("这是一个测试机构");
        service.insert(o);
        o.setJname("测试机构A");
        service.updateByPrimaryKey(o);
    }

    @Test
    @Rollback
    @Transactional
    public void getAll() {
        List<organization> os = service.getAll();
        if(os!=null){
            for(organization o:os){
                System.out.println(o.getJid());
            }
        }else {
            System.out.println("不存在");
        }
    }

    @Test
    @Rollback
    @Transactional
    public void selectByJName() {
        List<organization> os = service.selectByJName("测试机构B");
        if(os!=null){
            for(organization o:os){
                System.out.println(o.getJid());
            }
        }else {
            System.out.println("不存在");
        }
    }
}