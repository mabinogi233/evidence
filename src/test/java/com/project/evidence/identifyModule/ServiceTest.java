package com.project.evidence.identifyModule;

import com.project.evidence.identifyModule.database.entity.identify;
import com.project.evidence.identifyModule.database.entity.identifyProvide;
import com.project.evidence.identifyModule.database.entity.identifyResult;
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
    public void deleteIdentifyByPrimaryKey() {
        service.deleteIdentifyByPrimaryKey(0);
        System.out.println(service.selectIdentifyByPrimaryKey(0));
    }

    @Test
    @Rollback
    @Transactional
    public void insertIdentify() {
        identify i = new identify();
        i.setUid(0);
        i.setState("已提交");
        i.setQid(0);
        i.setSubmitdate(new Date());
        i.setWid(0);
        service.insertIdentify(i);
        System.out.println(service.selectIdentifyByWid(0).size());
    }

    @Test
    @Rollback
    @Transactional
    public void updateIdentifyByPrimaryKey() {
        identify i = service.selectIdentifyByPrimaryKey(0);
        i.setState("鉴定中");
        service.updateIdentifyByPrimaryKey(i);
        System.out.println(service.selectIdentifyByPrimaryKey(0).getState());
    }

    @Test
    @Rollback
    @Transactional
    public void deleteIdentifyProvideByPrimaryKey() {
        service.deleteIdentifyProvideByPrimaryKey(0);
        System.out.println(service.selectIdentifyProvideByPrimaryKey(0));
    }

    @Test
    @Rollback
    @Transactional
    public void insertIdentifyProvide() {
        identifyProvide provide = new identifyProvide();
        provide.setJid(2);
        provide.setIdentifytext("测试鉴定2");
        service.insertIdentifyProvide(provide);
        System.out.println(service.selectIdentifyProvideByJid(2).get(1).getIdentifytext());
    }

    @Test
    @Rollback
    @Transactional
    public void updateIdentifyProvideByPrimaryKey() {
        identifyProvide provide = service.selectIdentifyProvideByPrimaryKey(0);
        provide.setIdentifytext("测试鉴定3");
        service.updateIdentifyProvideByPrimaryKey(provide);
        System.out.println(service.selectIdentifyProvideByPrimaryKey(0).getIdentifytext());
    }

    @Test
    @Rollback
    @Transactional
    public void deleteIdentifyResultByPrimaryKey() {
        service.deleteIdentifyResultByPrimaryKey(0);
        System.out.println(service.selectIdentifyResultByPrimaryKey(0));
    }

    @Test
    @Rollback
    @Transactional
    public void insertIdentifyResult() {
        identifyResult r = new identifyResult();
        r.setJid(2);
        r.setBid(1);
        r.setRtext("junit单元测试");
        r.setProvidedate(new Date());
        service.insertIdentifyResult(r);
        System.out.println(service.selectIdentifyResultByPrimaryKey(1).getRtext());
    }

    @Test
    @Rollback
    @Transactional
    public void updateIdentifyResultByPrimaryKey() {
        identifyResult r = service.selectIdentifyResultByPrimaryKey(0);
        r.setRtext("更新");
        service.updateIdentifyResultByPrimaryKey(r);
        System.out.println(service.selectIdentifyResultByPrimaryKey(0).getRtext());
    }

    @Test
    public void selectIdentifyByPrimaryKey() {
        System.out.println(service.selectIdentifyByPrimaryKey(0).getWid());
    }

    @Test
    public void selectIdentifyByWid() {
        System.out.println(service.selectIdentifyByWid(0).size());
    }

    @Test
    public void selectIdentifyByUid() {
        System.out.println(service.selectIdentifyByUid(0).size());
    }

    @Test
    public void selectIdentifyByQid() {
        System.out.println(service.selectIdentifyByUid(0).size());
    }

    @Test
    public void selectIdentifyProvideByJid() {
        System.out.println(service.selectIdentifyProvideByJid(2).get(0).getIdentifytext());
    }

    @Test
    public void selectIdentifyProvideByPrimaryKey() {
        System.out.println(service.selectIdentifyProvideByPrimaryKey(0).getIdentifytext());
    }

    @Test
    public void selectIdentifyResultByJid() {
        System.out.println(service.selectIdentifyResultByJid(2).get(0).getRtext());
    }

    @Test
    public void selectIdentifyResultByPrimaryKey() {
        System.out.println(service.selectIdentifyResultByPrimaryKey(0).getRtext());
    }

    @Test
    public void getAllProvide() {
        System.out.println(service.getAllProvide().get(0).getIdentifytext());
    }
}