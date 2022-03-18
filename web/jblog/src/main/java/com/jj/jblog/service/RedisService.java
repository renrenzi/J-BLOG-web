package com.jj.jblog.service;

/**
 *  Redis操作Service
 * @author 任人子
 * @date 2022/3/18  - {TIME}
 */
public interface RedisService {
    /**
     * 保存属性
     * @param key
     * @param value
     */
    void set(String key, Object value);
    /**
     * 获取属性
     * @param key
     * @return
     */
    Object get(String key);
    /**
     * 设置过期时间
     * @param key
     * @param time
     * @return
     */
    Boolean expire(String key, long time);
}
