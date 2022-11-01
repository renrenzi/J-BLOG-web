package com.jj.jblog.basic;

import com.jj.jblog.constant.HttpStatusEnum;

/**
 * 响应结果生成工具
 * @author 任人子
 * @date 2021/11/10  - {TIME}
 */
public class ResultGenerator {

    public ResultGenerator() {

    }
    public static <T> Result<T> getResultByMessage(String message) {
        Result<T> result = new Result<T>();
        result.setMessage(message);
        return result;
    }
    public static <T> Result<T> getResultByErrorCode(IErrorCode errorCode) {
        Result<T> result = new Result<T>();
        result.setData((T) errorCode);
        return result;
    }
    public static <T> Result<T> getResultByHttp(HttpStatusEnum constants, String msg, T data) {
        Result<T> result = new Result<T>();
        result.setCode(constants.getCode());
        result.setMessage(msg);
        result.setData(data);
        return result;
    }

    /**
     * 自定义提示消息
     *
     * @param constants
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> getResultByHttp(HttpStatusEnum constants, T data) {
        Result<T> result = new Result<T>();
        result.setCode(constants.getCode());
        result.setMessage(constants.getContent());
        result.setData(data);
        return result;
    }

    /**
     * 自定义提示消息
     *
     * @param constants
     * @param <T>
     * @return
     */
    public static <T> Result<T> getResultByHttp(HttpStatusEnum constants) {
        Result<T> result = new Result<T>();
        result.setCode(constants.getCode());
        result.setMessage(constants.getContent());
        return result;
    }

    /**
     * 自定义提示消息
     *
     * @param constants
     * @param <T>
     * @return
     */
    public static <T> Result<T> getResultByMsg(HttpStatusEnum constants, String message) {
        Result<T> result = new Result<T>();
        result.setCode(constants.getCode());
        result.setMessage(message);
        return result;
    }
}
