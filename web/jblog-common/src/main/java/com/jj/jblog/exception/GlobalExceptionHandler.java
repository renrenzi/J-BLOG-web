package com.jj.jblog.exception;

import cn.dev33.satoken.exception.*;
import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.HttpStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常拦截处理
 * @author 任人子
 * @date 2022/5/12  - {TIME}
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 用户未登录
     * @return
     */
    @ExceptionHandler(value = {NotLoginException.class})
    public Result handlerNoLoginException(NotLoginException e){
        return ResultGenerator.getResultByHttp(HttpStatusEnum.UN_LOGIN, e.getMessage());
    }

    /**
     * 用户不是该角色
     * @param e
     * @return
     */
    @ExceptionHandler(value = {NotRoleException.class})
    public Result handlerNotRoleException(NotRoleException e){
        return ResultGenerator.getResultByHttp(HttpStatusEnum.FORBIDDEN, e.getMessage());
    }

    /**
     * 用户没有该权限
     * @param e
     * @return
     */
    @ExceptionHandler(value = {NotPermissionException.class})
    public Result handlerNotPermissionException(NotPermissionException e){
        return ResultGenerator.getResultByHttp(HttpStatusEnum.FORBIDDEN, e.getMessage());
    }


    @ExceptionHandler(value = ApiException.class)
    public Result handler(ApiException e){
        if(e.getErrorCode() != null){
            return ResultGenerator.getResultByErrorCode(e.getErrorCode());
        }
        return ResultGenerator.getResultByMessage(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.VALIDATION_ARGUMENT, message);
    }

    @ExceptionHandler(value = BindException.class)
    public Result handleValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.VALIDATION_ARGUMENT, message);
    }
  }
