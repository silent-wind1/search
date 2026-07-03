package com.yefeng.user.controller;

import com.yefeng.common.config.UserThreadLocal;
import com.yefeng.model.entity.User;
import com.yefeng.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userService ;

    @GetMapping(value = "/findUserByUserId/{userId}")
    public User findUserByUserId(@PathVariable Long userId) {
        Long user = UserThreadLocal.getUser();
        log.info("user:{}", user);
        return userService.findUserByUserId(userId) ;
    }

}