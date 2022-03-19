package com.jj.jblog.controller.blog;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jj.jblog.basic.PageCondition;
import com.jj.jblog.basic.PageResult;
import com.jj.jblog.basic.Result;
import com.jj.jblog.basic.ResultGenerator;
import com.jj.jblog.constant.HttpStatusEnum;
import com.jj.jblog.constant.UploadConstants;
import com.jj.jblog.dao.BlogInfoMapper;
import com.jj.jblog.entity.BlogInfo;
import com.jj.jblog.service.BlogInfoService;
import com.jj.jblog.util.DateUtils;
import com.jj.jblog.util.FastDfsUtil;
import com.jj.jblog.util.UploadFileUtil;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 张俊杰
 * @date 2021/11/5  - {TIME}
 */
@RequestMapping("info")
@RestController
public class BlogInfoController {

    @Resource
    private BlogInfoService blogInfoService;
    @Resource
    private BlogInfoMapper blogInfoMapper;

    /**
     * 保存文章图片 (!采用 FastDFS 文件上传)
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadFileByFastDFS")
    public Result<String> uploadFileByFastDFS(MultipartFile file) {
        if (ObjectUtils.isEmpty(file)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        String url = UploadFileUtil.getUploadFileUrl(file);

        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, url);
    }

    /**
     * 保存文章图片 (!!!!!！想采用 FastDFS 文件上传 --> 未实现)
     *
     * @param file
     * @return
     */
    @PostMapping("/uploadFile")
    public Map<String, Object> uploadFile(MultipartFile file) {
        String suffix = UploadFileUtil.getSuffixName(file);

        String newFileName = UploadFileUtil.getNewFileName(suffix);
        // 创建文件路径
        File fileDirectory = new File(UploadConstants.FILE_UPLOAD_DIC);
        // 创建目标文件
        File destFile = new File(UploadConstants.FILE_UPLOAD_DIC + newFileName);
        Map<String, Object> result = new HashMap<String, Object>(3);
        try {
            if (!fileDirectory.exists() && fileDirectory.mkdirs()) {
                throw new IOException("文件创建失败,路径为" + fileDirectory);
            }
            file.transferTo(destFile);
            String fileUrl = UploadConstants.FILE_UPLOAD_DIC + newFileName;
            result.put("success", 1);
            result.put("message", "上传成功");
            result.put("url", fileUrl);
        } catch (IOException e) {
            result.put("success", 0);
        }
        return result;
    }

    /**
     * 保存文章内容
     *
     * @param tagId
     * @param blogInfo
     * @return
     */
    @PostMapping("/saveBlog")
    public Result<String> saveBlog(Integer tagId, BlogInfo blogInfo) {
        if (StringUtils.isEmpty(tagId) || ObjectUtils.isEmpty(blogInfo)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return blogInfoService.saveBlog(tagId, blogInfo);
    }

    /**
     * 分页查询文章列表
     *
     * @param condition
     * @param blogInfo
     * @return
     */
    @PostMapping("/pageBlog")
    public Result<PageResult> pageBlog(PageCondition<BlogInfo> condition, BlogInfo blogInfo) {
        // 传参问题,未解决
        if ("".equals(blogInfo.getBlogTitle())) {
            blogInfo.setBlogTitle(null);
        }
        if (StringUtils.isEmpty(condition.getPageNum()) || StringUtils.isEmpty(condition.getPageSize())) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return blogInfoService.pageBlog(condition, blogInfo);
    }

    /**
     * 修改文章部分状态
     *
     * @param blogInfo
     * @return
     */
    @PostMapping("/blogStatus")
    public Result<String> updateBlogStatus(BlogInfo blogInfo) {
        if (ObjectUtils.isEmpty(blogInfo.getBlogId())) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return blogInfoService.updateBlogStatus(blogInfo);
    }

    /**
     * 更新文章删除状态
     *
     * @param blogId
     * @return
     */
    @PostMapping("/deleteBlog")
    public Result<String> deleteBlog(Long blogId) {
        if (StringUtils.isEmpty(blogId)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return blogInfoService.deleteBlog(blogId);
    }

    /**
     * 清除文章数据库数据
     *
     * @param blogId
     * @return
     */
    @PostMapping("/clearBlog")
    public Result<String> clearBlog(@RequestParam Long blogId) {
        if (StringUtils.isEmpty(blogId)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return blogInfoService.clearBlog(blogId);
    }

    /**
     * 还原文章
     *
     * @param blogId
     * @return
     */
    @PostMapping("/restoreBlog")
    public Result<String> restoreBlog(@RequestParam Long blogId) {
        if (StringUtils.isEmpty(blogId)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return blogInfoService.restoreBlog(blogId);
    }

    /**
     * 获取所有博客
     *
     * @return
     */
    @GetMapping("/getAllBlog")
    public Result<List<BlogInfo>> getAllBlog() {
        return blogInfoService.getAllBlog();
    }

    /**
     * 获取博客详细信息
     *
     * @param blogId
     * @return
     */
    @PostMapping("/getBlogInfo")
    public Result<BlogInfo> getBlogInfo(Long blogId) {
        if (StringUtils.isEmpty(blogId)) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK, blogInfoMapper.selectById(blogId));
    }

    /**
     * 修改文章内容
     *
     * @param blogInfo
     * @return
     */
    @PostMapping("/editBlogInfo")
    public Result<String> editBlogInfo(BlogInfo blogInfo) {
        if (ObjectUtils.isEmpty(blogInfo) || StringUtils.isEmpty(blogInfo.getBlogId())) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.BAD_REQUEST);
        }
        blogInfo.setUpdateTime(DateUtils.getLocalCurrentTime());
        int flag = blogInfoMapper.updateById(blogInfo);
        if (flag < 1) {
            return ResultGenerator.getResultByHttp(HttpStatusEnum.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.getResultByHttp(HttpStatusEnum.OK);
    }

}