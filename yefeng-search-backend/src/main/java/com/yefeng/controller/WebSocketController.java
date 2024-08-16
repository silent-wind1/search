package com.yefeng.controller;

import cn.hutool.core.date.DateUtil;
import com.yefeng.common.BaseResponse;
import com.yefeng.common.ResultUtils;
import com.yefeng.utils.WebSocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/webSocket")
public class WebSocketController {
    @Resource
    private WebSocketUtil webSocketUtil;

    /**
     * 发送全体消息
     *
     * @param message 消息内容
     * @return ok
     */
    @GetMapping(value = "/sendMessageAll/{message}")
    public BaseResponse<String> sendMessageAll(@PathVariable("message") String message) {
        webSocketUtil.sendToAllClient(message);
        log.info("{} | admin 全体消息-> {}", DateUtil.now(), message);
        return ResultUtils.success("消息推送成功：" + message);
    }

    /**
     * 发送消息给某人
     *
     * @return ok
     */
    @GetMapping(value = "/sendMessageTo")
    public BaseResponse sendMessageTo(@RequestParam String message, @RequestParam String userId) {
        webSocketUtil.sendToAllClient(message);
        log.info("{} | admin 私人消息-> {}", DateUtil.now(), message, userId);
        return ResultUtils.error(null);
    }
}

