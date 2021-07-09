package com.project.evidence.logModule;

//800-899
public enum ErrorCode {
    GET_LOG_SUCCESS("日志获取成功","800"),
    GET_LOG_FAIL("日志获取失败","801");
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
