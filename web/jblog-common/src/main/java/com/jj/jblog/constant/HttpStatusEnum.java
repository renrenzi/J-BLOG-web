package com.jj.jblog.constant;

/**
 * HTTP 请求状态常量
 * @author 张俊杰
 * @date 2021/11/10  - {TIME}
 */
public enum HttpStatusEnum {

    OK(2000,"成功"),
    BAD_REQUEST(4000,"请求参数有误"),
    UNAUTHORIZED(4001,"登录失败"),
    UN_LOGIN(4002,"该用户未登录"),
    FORBIDDEN(4003,"权限不足"),
    INTERNAL_SERVER_ERROR(5000,"服务器发生了一个无法预料的错误"),
    BAD_GATEWAY(5002,"从上游服务器接受到无效的响应"),
    VALIDATION_ARGUMENT(6666, "参数校验失败");

    private final int code;
    private final String content;

     HttpStatusEnum(int code, String content) {
        this.code = code;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public String getContent() {
        return content;
    }
}
