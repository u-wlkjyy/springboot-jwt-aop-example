package com.soft.permissondemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.soft.permissondemo.entity.User;

public interface IUserService extends IService<User> {
    /**
     * 获取用户的所有权限
     * @param userId 用户ID
     * @return 权限列表
     */
    java.util.Set<String> getUserPermissions(Integer userId);
} 