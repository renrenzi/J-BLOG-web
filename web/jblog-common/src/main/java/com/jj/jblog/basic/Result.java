package com.jj.jblog.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 任人子
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
