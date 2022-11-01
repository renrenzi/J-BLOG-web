package com.jj.jblog.pojo.dto;

import com.jj.jblog.entity.AdminUser;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户信息封装
 * @author 任人子
 * @date 2022/5/9  - {TIME}
 */
public class UserInfoDto extends AdminUser {
    @Getter
    @Setter
    private String imgUrl;
}
