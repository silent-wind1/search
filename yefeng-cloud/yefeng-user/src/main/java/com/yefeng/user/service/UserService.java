package com.yefeng.user.service;

import com.yefeng.model.entity.User;

public interface UserService {

    // 根据用户的id查询用户详情
    public abstract User findUserByUserId(Long userId) ;

}