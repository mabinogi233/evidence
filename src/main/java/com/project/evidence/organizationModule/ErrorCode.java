package com.project.evidence.organizationModule;


//100~199
public enum ErrorCode {

    UpdateSuccess("修改成功","100"),
    UpdateFail("修改失败","101"),
    DeleteSuccess("删除成功","102"),
    DeleteFail("删除失败","103"),
    InsertSuccess("添加成功","104"),
    InsertFail("添加失败","105"),
    SelectSuccess("查询成功","106"),
    SelectFail("查询失败","107"),
    PrimaryKeyError("PrimaryKey对应的对象不存在","108"),
    TextError("属性错误","109");
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
