package com.jj.jblog.basic;

/**
 * 错误码结果封装
 * @author 任人子
 * @date 2022/5/12  - {TIME}
 */
public class ResultCode implements IErrorCode{

    private Result result;

    public ResultCode(Result result) {
        this.result = result;
    }

    @Override
    public int getCode() {
        return result.getCode();
    }

    @Override
    public String getMessage() {
        return result.getMessage();
    }
}
