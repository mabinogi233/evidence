package com.project.evidence.evidenceModule.database.entity;

import java.util.Date;

public class evidence {
    private Integer wid;

    private String wtext;

    private Integer uid;

    private Date insertdate;

    private String state;

    public Integer getWid() {
        return wid;
    }

    public void setWid(Integer wid) {
        this.wid = wid;
    }

    public String getWtext() {
        return wtext;
    }

    public void setWtext(String wtext) {
        this.wtext = wtext == null ? null : wtext.trim();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
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