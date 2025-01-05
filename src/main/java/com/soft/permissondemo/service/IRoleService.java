package com.soft.permissondemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.soft.permissondemo.entity.Role;

public interface IRoleService extends IService<Role> {
    /**
     * 获取角色的所有权限
     * @param roleId 角色ID
     * @return 权限列表
     */
    java.util.Set<String> getRolePermissions(Integer roleId);
} 