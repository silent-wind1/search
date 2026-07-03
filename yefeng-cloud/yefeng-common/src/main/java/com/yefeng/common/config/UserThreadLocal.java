package com.yefeng.common.config;

/**
 * @author wind
 * @description:
 * @date 2026/7/3 17:11
 */
public class UserThreadLocal {
    public static final ThreadLocal<Long> userThreadLocal = new ThreadLocal<>();

    /**
     * 保存当前登录用户信息到ThreadLocal
     * @param userId 用户id
     */
    public static void setUser(Long userId) {
        userThreadLocal.set(userId);
    }

    /**
     * 获取当前登录用户信息
     * @return 用户id
     */
    public static Long getUser() {
        return userThreadLocal.get();
    }

    /**
     * 移除当前登录用户信息
     */
    public static void removeUser(){
        userThreadLocal.remove();
    }
}
