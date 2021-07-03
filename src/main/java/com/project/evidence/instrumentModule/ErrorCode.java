package com.project.evidence.instrumentModule;

//700-799
public enum ErrorCode {
    UpdateSuccess("修改成功","700"),
    UpdateFail("修改失败","701"),
    DeleteSuccess("删除成功","702"),
    DeleteFail("删除失败","703"),
    InsertSuccess("添加成功","704"),
    InsertFail("添加失败","705"),
    SelectSuccess("查询成功","706"),
    SelectFail("查询失败","707"),
    SelectNull("查询结果为空","708"),
    TextError("属性错误","709"),
    NotHasInstrument("仪器不存在","710"),
    NotHasUser("用户不存在","711"),
    UpdateFixItem("修改了不可修改的参数值","712");
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
