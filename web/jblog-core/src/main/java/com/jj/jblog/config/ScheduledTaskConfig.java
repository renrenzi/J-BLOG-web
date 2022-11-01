package com.jj.jblog.config;

import com.jj.jblog.constant.StringConstants;
import com.jj.jblog.entity.BlogInfo;
import com.jj.jblog.service.BlogInfoService;
import com.jj.jblog.service.RedisService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 定时任务配置
 * @author 任人子
 * @date 2022/4/8  - {TIME}
 */
@Configuration
public class ScheduledTaskConfig {

    @Resource
    private BlogInfoService blogInfoService;
    @Resource
    private RedisService redisService;

    /**
     * 每间隔 4h 将redis的点赞信息保存到数据库
     */
    @Scheduled(cron = "0 0 0/4 * * ?")
    public void cronSaveLikes() {
        Map<Object, Object> blogIdLikesMap = redisService.hGetAll(StringConstants.BLOG_LIKES_COUNT_PREFIX);
        if(blogIdLikesMap.size() == 0) {
            return;
        }
        List<BlogInfo> blogInfoList = new ArrayList<>();
        for(Map.Entry<Object, Object> entry : blogIdLikesMap.entrySet()) {
            String blogId = (String) entry.getKey();
            Integer blogLikes = (Integer) entry.getValue();
            blogInfoList.add(new BlogInfo().setBlogId(Long.valueOf(blogId))
                                           .setBlogLikes(Long.valueOf(blogLikes)));
            redisService.hDel(StringConstants.BLOG_LIKES_COUNT_PREFIX, blogId);
        }
        blogInfoService.updateBatchById(blogInfoList);
    }
}
