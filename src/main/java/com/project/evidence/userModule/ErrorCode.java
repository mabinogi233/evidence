package com.project.evidence.userModule;


//200~299
public enum ErrorCode {

    UpdateSuccess("修改成功","200"),
    UpdateFail("修改失败","201"),
    DeleteSuccess("删除成功","202"),
    DeleteFail("删除失败","203"),
    InsertSuccess("添加成功","204"),
    InsertFail("添加失败","205"),
    SelectSuccess("查询成功","206"),
    SelectFail("查询失败","207"),
    PrimaryKeyError("PrimaryKey对应的对象不存在","208"),
    TextError("属性错误","209"),
    NotHasUser("用户不存在","210"),
    IdcardNumberError("身份证号错误","211"),
    AuthorityError("权限错误","212"),
    RoleError("身份错误","213"),
    HasUserName("用户名重复","214"),
    UpdateFixItem("修改了不可修改的参数值","215"),
    UpdateSuccessButNotUNLogin("仅修改用户名","216"),
    NotNeedUpdate("无需修改","217");

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
