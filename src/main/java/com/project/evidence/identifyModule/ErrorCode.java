package com.project.evidence.identifyModule;


//300-399
public enum ErrorCode {

    UpdateSuccess("修改成功","300"),
    UpdateFail("修改失败","301"),
    DeleteSuccess("删除成功","302"),
    DeleteFail("删除失败","303"),
    InsertSuccess("添加成功","304"),
    InsertFail("添加失败","305"),
    SelectSuccess("查询成功","306"),
    SelectNull("结果为空","307"),
    PrimaryKeyError("PrimaryKey对应的对象不存在","308"),
    TextError("属性错误","309"),
    UserError("用户异常","310"),
    UpdateFixItem("修改了不可修改的参数值","311");

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
