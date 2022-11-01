package com.jj.jblog.basic;

/**
 * 封装Api错误码
 * @author 任人子
 * @date 2022/5/12  - {TIME}
 */
public interface IErrorCode {
    /**
     * 获取状态码
     * @return
     */
    int getCode();

    /**
     * 获取信息
     * @return
     */
    String getMessage();
}
