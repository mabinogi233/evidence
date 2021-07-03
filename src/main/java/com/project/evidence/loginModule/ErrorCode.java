package com.project.evidence.loginModule;

//600-699
public enum ErrorCode {
    LoginSuccess("登录成功","600"),
    LoginFail("登录失败","601"),
    SysError("系统错误","602"),
    UnLoginSuccess("退出登录成功","603"),
    UnLoginFail("退出登录失败","604");

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
