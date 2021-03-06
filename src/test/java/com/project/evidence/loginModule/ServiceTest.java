package com.project.evidence.loginModule;

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
    public void login() {
        System.out.println(service.login("ceshi","123456"));
    }

    @Test
    @Rollback
    @Transactional
    public void unlogin() {
        System.out.println(service.unlogin(0));
        System.out.println(service.selectByUid(0));
    }

    @Test
    @Rollback
    @Transactional
    public void checkToken() {
        System.out.println(service.checkToken("Rp6npIeiQ93mSFpsmeg7gA=="));
    }

    @Test
    @Rollback
    @Transactional
    public void refToken() {
        System.out.println(service.refToken("PC2nEJIAklEfDU+43Kyicg==").equals("Rp6npIeiQ93mSFpsmeg7gA=="));
    }

    @Test
    @Rollback
    @Transactional
    public void selectByUid() {
        System.out.println(service.selectByUid(0).getToken());
    }
}