package com.soft.permissondemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft.permissondemo.entity.Permission;
import com.soft.permissondemo.entity.Role;
import com.soft.permissondemo.entity.RolePermissions;
import com.soft.permissondemo.mapper.PermissionMapper;
import com.soft.permissondemo.mapper.RoleMapper;
import com.soft.permissondemo.mapper.RolePermissionsMapper;
import com.soft.permissondemo.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    
    @Autowired
    private RolePermissionsMapper rolePermissionsMapper;
    
    @Autowired
    private PermissionMapper permissionMapper;
    
    @Override
    public Set<String> getRolePermissions(Integer roleId) {
        // 1. 通过roleId查询role_permissions表获取权限ID列表
        List<Integer> permissionIds = rolePermissionsMapper.selectList(
            new LambdaQueryWrapper<RolePermissions>()
                .eq(RolePermissions::getRoleId, roleId)
        ).stream().map(RolePermissions::getPermissionId).collect(Collectors.toList());
        
        // 2. 如果角色没有权限，直接返回空集合
        if (permissionIds.isEmpty()) {
            return new HashSet<>();
        }
        
        // 3. 通过权限ID列表查询permissions表获取权限标识
        List<Permission> permissions = permissionMapper.selectList(
            new LambdaQueryWrapper<Permission>()
                .in(Permission::getId, permissionIds)
        );
        
        // 4. 提取权限标识并返回
        return permissions.stream()
            .map(Permission::getK)
            .collect(Collectors.toSet());
    }
} 