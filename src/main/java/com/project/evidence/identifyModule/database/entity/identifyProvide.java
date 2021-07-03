package com.project.evidence.identifyModule.database.entity;

public class identifyProvide {
    private Integer qid;

    private Integer jid;

    private String identifytext;

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    public Integer getJid() {
        return jid;
    }

    public void setJid(Integer jid) {
        this.jid = jid;
    }

    public String getIdentifytext() {
        return identifytext;
    }

    public void setIdentifytext(String identifytext) {
        this.identifytext = identifytext == null ? null : identifytext.trim();
    }
}