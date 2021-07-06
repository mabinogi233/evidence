package com.project.evidence.evidenceModule;

import com.project.evidence.evidenceModule.database.entity.evidence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
        System.out.println(service.selectByWid(0));
    }

    @Test
    @Rollback
    @Transactional
    public void insert() {
        evidence e = new evidence();
        e.setUid(0);
        e.setState("正常");
        e.setWtext("这是单元测试哦");
        e.setInsertdate(new Date());
        service.insert(e);
        System.out.println(service.selectByText("单元测试").get(0).getWid());
    }

    @Test
    @Rollback
    @Transactional
    public void updateByPrimaryKey() {
        evidence e = service.selectByWid(0);
        e.setWtext("单元测试");
        e.setInsertdate(new Date());
        service.updateByPrimaryKey(e);
        System.out.println(service.selectByWid(0).getWtext());
    }

    @Test
    public void selectByUid() {
        System.out.println(service.selectByUid(0).get(0).getWtext());
    }

    @Test
    public void selectByWid() {
        System.out.println(service.selectByWid(0).getWtext());
    }

    @Test
    public void selectByText() {
        System.out.println(service.selectByText("测试").get(0).getWtext());
    }

    @Test
    public void selectAll() {
        System.out.println(service.selectAll().get(0).getWtext());
    }
}