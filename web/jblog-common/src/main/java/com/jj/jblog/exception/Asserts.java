package com.jj.jblog.exception;

import com.jj.jblog.basic.IErrorCode;

/**
 * 断言处理类, 用与处理各种Api处理类
 * @author 任人子
 * @date 2022/5/12  - {TIME}
 */
public class Asserts {
    public static void fail(IErrorCode errorCode){
        throw new ApiException(errorCode);
    }

    public static void fail(String message){
        throw new ApiException(message);
    }
}
