package com.jj.jblog.pojo.dto;

import lombok.Data;

/**
 * 前台博客请求条件封装
 *
 * @author 张俊杰
 * @date 2021/11/13  - {TIME}
 */
@Data
public class BlogViewRequest {

    private Integer pageNum;

    private Integer pageSize;

    private Integer tagId;

    private String categoryName;

    private String keyWord;
}
