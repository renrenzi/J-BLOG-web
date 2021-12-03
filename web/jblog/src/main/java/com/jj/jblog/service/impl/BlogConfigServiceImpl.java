package com.jj.jblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jj.jblog.dao.BlogConfigMapper;
import com.jj.jblog.entity.BlogConfig;
import com.jj.jblog.service.BlogConfigService;
import org.springframework.stereotype.Service;

/**
 * @author 张俊杰
 * @date 2021/11/18  - {TIME}
 */
@Service
public class BlogConfigServiceImpl extends ServiceImpl<BlogConfigMapper, BlogConfig> implements BlogConfigService {
}
