package com.movie.Enums;

public enum ExceptionEnums {
    UNKNOW_ERROR(-1,"未知错误异常"),
    DEL_SUCCESS(0,"删除成功"),
    UNFIND_Player_ERROR(2,"用户不存在"),
    UNFIND_ITEM_ERROR(3,"道具不存在"),
    USER_TEL_ERROR(4,"手机号错误"),
    PASSWORD_ERROR(5,"用户名密码错误"),
    LOGIN_ERROR(6,"登录错误，请重新登录"),
    TEAM_ADD_USER_SUCCESS(7,"添加队员成功"),
    POST_ERROR(8,"岗位不存在"),
    APPLY_SUCCESS(9,"申请成功"),
    POST_DESIGN_ERROE(10,"岗位不存在"),
    PROJECT_ERROR(11,"项目不存在"),
    JOB_ID_ERROR(12,"岗位没有绑定"),
    UNFIND_DATA_ERROR(13,"数据项不存在"),
    ADD_ERROR(14,"添加失败")
    ;

    private  Integer code;
    private  String msg;
    ExceptionEnums(Integer code,String msg) {
        this.code=code;
        this.msg=msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
