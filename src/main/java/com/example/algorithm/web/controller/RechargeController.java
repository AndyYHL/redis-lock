package com.example.algorithm.web.controller;

import cn.hutool.core.thread.ThreadUtil;
import com.example.algorithm.lock.RedisLock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 充值
 * <p>
 * User: wuz
 * Date: 2019-10-02
 */
@RestController
@RequestMapping("/inward/recharge")
public class RechargeController {
    @RedisLock(key = "standard", retryTimes = 5, expire = 120000)
    @GetMapping(value = "/standard")
    public String standardRecharge(@RequestParam(value = "rechargeNo") String rechargeN) {
        ThreadUtil.sleep(20000);
        return rechargeN.concat("参数请求");
    }
}
