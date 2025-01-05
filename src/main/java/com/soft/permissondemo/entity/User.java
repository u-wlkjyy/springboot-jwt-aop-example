package com.soft.permissondemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: wlkjyy <wlkjyy@vip.qq.com>
 * @Date: 2025/1/5
 * @Project: permissondemo
 */
@Data
@TableName("users")
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;
}
