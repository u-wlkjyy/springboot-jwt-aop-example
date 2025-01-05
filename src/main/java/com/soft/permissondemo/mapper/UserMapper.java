package com.soft.permissondemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft.permissondemo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
} 