package com.jj.jblog.controller.blog;

import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.DeleteStatusEnum;
import com.jj.jblog.constant.HttpStatusEnum;
import com.jj.jblog.constant.LinkConstants;
import com.jj.jblog.entity.BlogLink;
import com.jj.jblog.service.BlogLinkService;
import com.jj.jblog.util.DateUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 张俊杰
 * @date 2021/11/19  - {TIME}
 */
@RestController
@RequestMapping("/link")
public class BlogLinkController {
    @Resource
    private BlogLinkService blogLinkService;

    /**
     * 获取链接类型列表
     *
     * @return
     */
    @GetMapping("/getLinkTypeList")
    public Result<List<BlogLink>> getLinkTypeList() {
        List<BlogLink> result = new ArrayList<BlogLink>();
        result.add(new BlogLink().setLinkType(LinkConstants.LINK_TYPE_FRIENDSHIP.getLinkTypeId())
                .setLinkName(LinkConstants.LINK_TYPE_FRIENDSHIP.getLinkTypeName()));
        result.add(new BlogLink().setLinkType(LinkConstants.LINK_TYPE_RECOMMENDED.getLinkTypeId())
                .setLinkName(LinkConstants.LINK_TYPE_RECOMMENDED.getLinkTypeName()));
        result.add(new BlogLink().setLinkType(LinkConstants.LINK_TYPE_PRIVATE.getLinkTypeId())
                .setLinkName(LinkConstants.LINK_TYPE_PRIVATE.getLinkTypeName()));
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, result);
    }

    /**
     * 分页查询链接列表
     *
     * @param condition
     * @param blogLink
     * @return
     */
    @PostMapping("/pageLinkList")
    public Result<PageResult<BlogLink>> pageLinkList(PageCondition<BlogLink> condition, BlogLink blogLink) {
        if (StringUtils.isEmpty(condition.getPageNum()) || StringUtils.isEmpty(condition.getPageSize())) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return blogLinkService.pageLinkList(condition, blogLink);
    }

    /**
     * 添加链接
     *
     * @param blogLink
     * @return
     */
    @PostMapping("/addLink")
    public Result<String> addLink(BlogLink blogLink) {
        if (ObjectUtils.isEmpty(blogLink)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        blogLink.setCreateTime(DateUtils.getLocalCurrentTime())
                .setIsDeleted(DeleteStatusEnum.NOT_DELETED.getStatus());
        if (!blogLinkService.save(blogLink)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    /**
     * 修改删除状态
     *
     * @param blogLink
     * @return
     */
    @PostMapping("/isDel")
    public Result<String> isDel(BlogLink blogLink) {
        if (ObjectUtils.isEmpty(blogLink)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        if (!blogLinkService.updateById(blogLink)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    /**
     * 清除链接信息
     *
     * @param linkId
     * @return
     */
    @PostMapping("/clearLink")
    public Result<String> clearLink(Integer linkId) {
        if (StringUtils.isEmpty(linkId)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        if (!blogLinkService.removeById(linkId)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

    /**
     * 获取链接
     *
     * @param linkId
     * @return
     */
    @PostMapping("/getLink")
    public Result<BlogLink> getLink(Integer linkId) {
        if (StringUtils.isEmpty(linkId)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        BlogLink blogLink = blogLinkService.getById(linkId);
        if (ObjectUtils.isEmpty(blogLink)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, blogLink);
    }

    /**
     * 修改链接信息
     *
     * @param blogLink
     * @return
     */
    @PostMapping("/editLink")
    public Result<String> editLinkOrSave(BlogLink blogLink) {
        if (ObjectUtils.isEmpty(blogLink)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        boolean flag = false;
        if (blogLink.getLinkId() == null) {
            flag = blogLinkService.save(blogLink.setCreateTime(DateUtils.getLocalCurrentTime()));
        }else {
            flag = blogLinkService.updateById(blogLink);
        }
        if (!flag) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }


}
