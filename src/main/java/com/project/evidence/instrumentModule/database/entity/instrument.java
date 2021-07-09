package com.project.evidence.instrumentModule.database.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class instrument {
    private Integer yid;

    private String ytext;

    private Integer jid;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date insertdate;

    private String state;

    public Integer getYid() {
        return yid;
    }

    public void setYid(Integer yid) {
        this.yid = yid;
    }

    public String getYtext() {
        return ytext;
    }

    public void setYtext(String ytext) {
        this.ytext = ytext == null ? null : ytext.trim();
    }

    public Integer getJid() {
        return jid;
    }

    public void setJid(Integer jid) {
        this.jid = jid;
    }

    public Date getInsertdate() {
        return insertdate;
    }

    public void setInsertdate(Date insertdate) {
        this.insertdate = insertdate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}