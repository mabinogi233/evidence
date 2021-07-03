package com.project.evidence.instrumentModule;

import com.project.evidence.instrumentModule.database.entity.instrument;
import org.junit.jupiter.api.Test;
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
class ServiceTest {

    @Autowired
    Service service;

    @Test
    @Rollback
    @Transactional
    void deleteByPrimaryKey() {
        service.deleteByPrimaryKey(0);
        System.out.println(service.selectByYid(0));
    }

    @Test
    @Rollback
    @Transactional
    void insert() {
        instrument i = new instrument();
        i.setState("正常");
        i.setInsertdate(new Date());
        i.setYtext("测试物证2");
        i.setJid(2);
        service.insert(i);
        System.out.println(service.selectByJid(2).get(1).getYtext());
    }

    @Test
    @Rollback
    @Transactional
    void updateByPrimaryKey() {
        instrument i = service.selectByYid(0);
        i.setYtext("更新测试");
        service.updateByPrimaryKey(i);
        System.out.println(service.selectByYid(0).getYtext());
    }

    @Test
    void selectByJid() {
        System.out.println(service.selectByJid(2).get(0).getYtext());
    }

    @Test
    void selectByYid() {
        System.out.println(service.selectByYid(0).getYtext());
    }
}