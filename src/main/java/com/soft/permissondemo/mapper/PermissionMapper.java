package com.soft.permissondemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft.permissondemo.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
} 