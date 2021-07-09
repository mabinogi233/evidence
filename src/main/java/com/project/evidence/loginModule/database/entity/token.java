package com.project.evidence.loginModule.database.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class token {
    private Integer uid;

    private String token;

    private String reftoken;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date tokengettime;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date tokendeadtime;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date reftokendeadtime;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getReftoken() {
        return reftoken;
    }

    public void setReftoken(String reftoken) {
        this.reftoken = reftoken == null ? null : reftoken.trim();
    }

    public Date getTokengettime() {
        return tokengettime;
    }

    public void setTokengettime(Date tokengettime) {
        this.tokengettime = tokengettime;
    }

    public Date getTokendeadtime() {
        return tokendeadtime;
    }

    public void setTokendeadtime(Date tokendeadtime) {
        this.tokendeadtime = tokendeadtime;
    }

    public Date getReftokendeadtime() {
        return reftokendeadtime;
    }

    public void setReftokendeadtime(Date reftokendeadtime) {
        this.reftokendeadtime = reftokendeadtime;
    }
}