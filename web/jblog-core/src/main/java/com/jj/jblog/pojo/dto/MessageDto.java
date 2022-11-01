package com.jj.jblog.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息类封装
 * @author 任人子
 * @date 2022/7/7  - {TIME}
 */
@TableName(value = "tb_admin_img")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MessageDto {

    /**
     * 发送者Id
     */
    private Integer userId;
    /**
     *  消息类型
     */
    private String topic;
    /**
     *  消息Id
     */
    private Integer messageId;
    /**
     *  消息类型
     */
    private Integer messageType;
    /**
     *  接收者Id
     */
    private Integer messageUserId;
    /**
     *  其他请求数据
     */
    private Map<String, Object> data = new HashMap<>();

}
