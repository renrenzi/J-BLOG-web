package com.jj.jblog.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  Redis操作Service
 * @author 任人子
 * @date 2022/3/18  - {TIME}
 */
public interface RedisService {

    /**
     * String结构保存属性
     * @param key
     * @param value
     */
    void set(String key, Object value);
    /**
     * String结构获取属性
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * String结构删除key
     * @param key
     */
    void del(String key);
    /**
     * String结构设置过期时间
     * @param key
     * @param time
     * @return
     */
    Boolean expire(String key, long time);

    /**
     * 获取Hash结构中的属性
     * @param key
     * @param hashKey
     * @return
     */
    Object hGet(String key, String hashKey);

    /**
     * 向Hash结构中放入一个属性
     * @param key
     * @param hashKey
     * @param value
     * @param time
     * @return
     */
    Boolean hSet(String key, String hashKey, Object value, long time);

    /**
     * 向Hash结构中放入一个属性
     * @param key
     * @param hashKey
     * @param value
     */
    void hSet(String key, String hashKey, Object value);

    /**
     * 向Hash放入整个Nap
     * @param key
     * @param map
     */
    void hSetAll(String key, Map<String, ?> map);

    /**
     * 直接获取整个Hash结构
     * @param key
     * @return
     */
    Map<Object, Object> hGetAll(String key);

    /**
     * 删除Hash结构里的多个Key
     * @param key
     * @param keys
     */
    void hDel(String key, Object ... keys);

    /**
     *  往set 结构里面添加元素
     * @param key
     * @param values
     */
    void setAdd(String key, Object ... values);

    /**
     * 获取set结果
     * @param key
     * @return
     */
    Set<Object> getSet(String key);


    /**
     * 向List头插入数据
     * @param key
     * @param value
     */
    void lPush(String key, Object value);


    /**
     * 向List头插入数据
     * @param key
     * @param value
     */
    void rPush(String key, Object value);

    /**
     * 获取某一范围的List
     * @param key
     * @param start
     * @param end
     * @return
     */
    List<Object> lRange(String key, long start, long end);

    /**
     * 向BitMap插入一条数据
     * @param key
     * @param value
     */
    void bitSet(String key, long value);

    /**
     * @param key
     * @param value
     * @return
     */
    boolean getBit(String key, long value);
}
