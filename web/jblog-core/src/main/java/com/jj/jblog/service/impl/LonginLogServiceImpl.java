package com.jj.jblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jj.jblog.dao.LoginLogMapper;
import com.jj.jblog.entity.LoginLog;
import com.jj.jblog.service.LonginLogService;
import org.springframework.stereotype.Service;

/**
 * 登录日志 ServiceImpl
 * @author 任人子
 * @date 2022/4/10  - {TIME}
 */
@Service
public class LonginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LonginLogService {
}
