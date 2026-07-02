package com.yefeng.user.service.impl;

import com.yefeng.user.entity.User;
import com.yefeng.user.mapper.UserMapper;
import com.yefeng.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper ;

    @Override
    public User findUserByUserId(Long userId) {
        return userMapper.findUserByUserId(userId);
    }

}