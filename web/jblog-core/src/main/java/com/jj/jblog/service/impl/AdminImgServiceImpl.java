package com.jj.jblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jj.jblog.dao.AdminImgMapper;
import com.jj.jblog.entity.AdminImg;
import com.jj.jblog.service.AdminImgService;
import org.springframework.stereotype.Service;

/**
 * 用户头像ServiceImpl
 * @author 任人子
 * @date 2021/11/26  - {TIME}
 */
@Service
public class AdminImgServiceImpl extends ServiceImpl<AdminImgMapper, AdminImg> implements AdminImgService {
}
