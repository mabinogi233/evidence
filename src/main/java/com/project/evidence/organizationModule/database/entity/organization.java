package com.project.evidence.organizationModule.database.entity;

public class organization {
    private Integer jid;

    private String jname;

    private String jtext;

    public Integer getJid() {
        return jid;
    }

    public void setJid(Integer jid) {
        this.jid = jid;
    }

    public String getJname() {
        return jname;
    }

    public void setJname(String jname) {
        this.jname = jname == null ? null : jname.trim();
    }

    public String getJtext() {
        return jtext;
    }

    public void setJtext(String jtext) {
        this.jtext = jtext == null ? null : jtext.trim();
    }
}