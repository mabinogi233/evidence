package com.project.evidence.identifyModule.database.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class identifyResult {
    private Integer bid;

    private String rtext;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date providedate;

    private Integer jid;

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getRtext() {
        return rtext;
    }

    public void setRtext(String rtext) {
        this.rtext = rtext == null ? null : rtext.trim();
    }

    public Date getProvidedate() {
        return providedate;
    }

    public void setProvidedate(Date providedate) {
        this.providedate = providedate;
    }

    public Integer getJid() {
        return jid;
    }

    public void setJid(Integer jid) {
        this.jid = jid;
    }
}