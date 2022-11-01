package com.jj.jblog.constant;

/**
 * 项目状态枚举类
 * @author 任人子
 * @date 2021/11/29  - {TIME}
 */
public enum  BlogStatusEnum {

    RELEASE(1,"已发布"),
    NOT_RELEASE(0,"未发布");

    private final int status;
    private final String note;

    BlogStatusEnum(int status, String note) {
        this.status = status;
        this.note = note;
    }

    public int getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }
}
