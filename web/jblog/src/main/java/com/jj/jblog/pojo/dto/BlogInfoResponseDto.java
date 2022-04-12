package com.jj.jblog.pojo.dto;

import com.jj.jblog.entity.BlogInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * 封装blogInfo返回Dto
 * @author 任人子
 * @date 2022/4/11  - {TIME}
 */
public class BlogInfoResponseDto extends BlogInfo {

    @Getter
    @Setter
    private String categoryIcon;
}
