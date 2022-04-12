package com.jj.jblog.pojo.dto;

import com.jj.jblog.entity.BlogInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 封装BlogInfo请求Dto
 * @author 任人子
 * @date 2022/4/10  - {TIME}
 */

public class BlogInfoRequestDto extends BlogInfo {

    @Getter
    @Setter
    private List<Integer> tagIds;

}
