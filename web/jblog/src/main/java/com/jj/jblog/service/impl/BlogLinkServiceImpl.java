package com.jj.jblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.HttpStatusEnum;
import com.jj.jblog.dao.BlogLinkMapper;
import com.jj.jblog.entity.BlogLink;
import com.jj.jblog.service.BlogLinkService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 张俊杰
 * @date 2021/11/19  - {TIME}
 */
@Service
public class BlogLinkServiceImpl extends ServiceImpl<BlogLinkMapper, BlogLink> implements BlogLinkService {

    @Resource
    private BlogLinkMapper blogLinkMapper;

    @Override
    public Result<PageResult<BlogLink>> pageLinkList(PageCondition<BlogLink> condition, BlogLink blogLink) {
        QueryWrapper<BlogLink> query = new QueryWrapper<BlogLink>(blogLink);
        query.lambda().orderByDesc(BlogLink::getLinkRank);
        Page<BlogLink> page = new Page<BlogLink>(condition.getPageNum(), condition.getPageSize());
        blogLinkMapper.selectPage(page, query);
        List<BlogLink> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        PageResult<BlogLink> result = new PageResult<>();
        result.setData(records);
        result.setTotalSize(page.getTotal());
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, result);
    }
}
