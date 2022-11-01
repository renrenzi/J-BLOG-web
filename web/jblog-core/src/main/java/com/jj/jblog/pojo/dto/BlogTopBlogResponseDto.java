package com.jj.jblog.pojo.dto;

import com.jj.jblog.entity.BlogComment;
import com.jj.jblog.entity.BlogInfo;
import lombok.Data;
import java.util.List;

/**
 * 热门文章结果封装
 * @author 任人子
 * @date 2022/5/21  - {TIME}
 */
@Data
public class BlogTopBlogResponseDto {
    List<BlogInfo> blogInfoList;
    List<BlogInfo> blogCommentList;
}
