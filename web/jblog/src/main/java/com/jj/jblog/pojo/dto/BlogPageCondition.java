package com.jj.jblog.pojo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 张俊杰
 * @date 2021/11/23  - {TIME}
 */
@Data
@Accessors(chain = true)
public class BlogPageCondition {

    private Integer pageNum;
    private Integer pageSize;
}
