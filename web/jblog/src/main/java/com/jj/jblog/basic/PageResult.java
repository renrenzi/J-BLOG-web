package com.jj.jblog.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * @author 张俊杰
 * @date 2021/11/11  - {TIME}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult <T> {

    private Integer code;

    private String message;

    private Long totalSize;

    private List<T> data;


}
