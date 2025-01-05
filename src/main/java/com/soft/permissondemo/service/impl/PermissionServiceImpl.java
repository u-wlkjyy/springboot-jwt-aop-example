package com.soft.permissondemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft.permissondemo.entity.Permission;
import com.soft.permissondemo.mapper.PermissionMapper;
import com.soft.permissondemo.service.IPermissionService;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {
} 