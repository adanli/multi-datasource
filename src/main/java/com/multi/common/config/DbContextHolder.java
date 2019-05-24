package com.multi.common.config;

import com.multi.common.constant.DatabaseTypeEnum;

/**
 * @author adanl
 */
public class DbContextHolder {
    private static final ThreadLocal contextHolder = new ThreadLocal<>();
    /**
     * 设置数据源
     * @param dbTypeEnum
     */
    public static void setDbType(DatabaseTypeEnum dbTypeEnum) {
        contextHolder.set(dbTypeEnum.name());
    }
    /**
     * 取得当前数据源
     * @return
     */
    public static String getDbType() {
        return (String) contextHolder.get();
    }
    /**
     * 清除上下文数据
     */
    public static void clearDbType() {
        contextHolder.remove();
    }
}
