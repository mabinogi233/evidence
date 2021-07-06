package com.project.evidence.authorityModule;

//0~99
public enum ErrorCode {

    TokenError("token无效","000"),
    RoleError("不满足身份","001"),
    AuthorityError("不满足权限","002"),
    SystemError("系统错误","003");

    String text;

    String code;

    private ErrorCode(String text, String code) {
        this.text = text;
        this.code = code;
    }


    public String getCode() {
        return code;
    }

    public String getText(){
        return text;
    }
}