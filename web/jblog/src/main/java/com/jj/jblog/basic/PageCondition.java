package com.jj.jblog.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页状态封装
 * @author 张俊杰
 * @date 2021/11/11  - {TIME}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageCondition <T>{

    private Integer pageNum;

    private Integer pageSize;

    private T data;

}
