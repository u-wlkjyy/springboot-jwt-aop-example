package com.soft.permissondemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft.permissondemo.entity.User;
import com.soft.permissondemo.entity.UserRoles;
import com.soft.permissondemo.mapper.UserMapper;
import com.soft.permissondemo.mapper.UserRolesMapper;
import com.soft.permissondemo.service.IRoleService;
import com.soft.permissondemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    
    @Autowired
    private IRoleService roleService;
    
    @Autowired
    private UserRolesMapper userRolesMapper;
    
    @Override
    public Set<String> getUserPermissions(Integer userId) {
        // 1. 获取用户的所有角色ID
        List<UserRoles> userRoles = userRolesMapper.selectList(
            new LambdaQueryWrapper<UserRoles>()
                .eq(UserRoles::getUserId, userId)
        );
        
        List<Integer> roleIds = userRoles.stream()
            .map(UserRoles::getRoleId)
            .collect(Collectors.toList());
        
        // 2. 如果用户没有角色，直接返回空集合
        if (roleIds.isEmpty()) {
            return new HashSet<>();
        }
        
        // 3. 获取所有角色的权限并合并
        Set<String> permissions = new HashSet<>();
        for (Integer roleId : roleIds) {
            Set<String> rolePermissions = roleService.getRolePermissions(roleId);
            permissions.addAll(rolePermissions);
        }
        
        return permissions;
    }
} 