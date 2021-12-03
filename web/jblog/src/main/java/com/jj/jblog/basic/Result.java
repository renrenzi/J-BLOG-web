package com.jj.jblog.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 张俊杰
 * @date 2021/11/5  - {TIME}
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result <T>{

    private int code;
    private String message;
    private T data;

}
