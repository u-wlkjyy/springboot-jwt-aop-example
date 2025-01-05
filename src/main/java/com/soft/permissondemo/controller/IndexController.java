package com.soft.permissondemo.controller;

import com.soft.permissondemo.annotation.HasPermissions;
import com.soft.permissondemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @Author: wlkjyy <wlkjyy@vip.qq.com>
 * @Date: 2025/1/5
 * @Project: permissondemo
 */
@RestController
@RequestMapping("/api")
public class IndexController {

    @Autowired
    IUserService userService;

    @GetMapping("/user/permission")
    @HasPermissions("read_article")
    public Set<String> getUserPermission(){
        return userService.getUserPermissions(1);
    }
}
