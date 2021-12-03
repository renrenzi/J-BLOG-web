package com.jj.jblog.constant;



/**
 * @author 张俊杰
 * @date 2021/11/10  - {TIME}
 */
public enum DeleteStatusEnum {

    DELETED(1, "已删除"),
    NOT_DELETED(0, "未删除");

    private final int status;
    private final String note;

    DeleteStatusEnum(int status, String note) {
        this.status = status;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public int getStatus() {
        return status;
    }
}
