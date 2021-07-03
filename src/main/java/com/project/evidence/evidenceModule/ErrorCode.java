package com.project.evidence.evidenceModule;


//400-499
public enum ErrorCode {

    UpdateSuccess("修改成功","400"),
    UpdateFail("修改失败","401"),
    DeleteSuccess("删除成功","402"),
    DeleteFail("删除失败","403"),
    InsertSuccess("添加成功","404"),
    InsertFail("添加失败","405"),
    SelectSuccess("查询成功","406"),
    SelectFail("查询失败","407"),
    SelectNull("查询结果为空","408"),
    TextError("属性错误","409"),
    NotHasEvidence("物证不存在","410"),
    NotHasUser("用户不存在","411"),
    UpdateFixItem("修改了不可修改的参数值","415");

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
