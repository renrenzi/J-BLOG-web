package com.jj.jblog.service;

import java.util.Map;

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

    /**
     * 获取Hash结构中的属性
     */
    Object hGet(String key, String hashKey);

    /**
     * 向Hash结构中放入一个属性
     */
    Boolean hSet(String key, String hashKey, Object value, long time);

    /**
     * 向Hash结构中放入一个属性
     */
    void hSet(String key, String hashKey, Object value);

    /**
     * 直接获取整个Hash结构
     */
    Map<Object, Object> hGetAll(String key);

    void hDel(String key, Object ... keys);

}
