package com.jj.jblog.util;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 时间工具类
 * @author 张俊杰
 * @date 2021/11/11  - {TIME}
 */
public class DateUtils {

    /**
     * 获取本地当前时间
     * @return
     */
    public static Timestamp getLocalCurrentTime(){
        return new Timestamp(new Date().getTime());
    }
}
