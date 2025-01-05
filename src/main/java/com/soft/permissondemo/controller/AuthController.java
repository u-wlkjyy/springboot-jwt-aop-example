package com.soft.permissondemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.soft.permissondemo.common.Result;
import com.soft.permissondemo.dto.LoginRequest;
import com.soft.permissondemo.dto.LoginResponse;
import com.soft.permissondemo.entity.User;
import com.soft.permissondemo.mapper.UserMapper;
import com.soft.permissondemo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            // 认证用户
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );

            // 获取用户信息
            User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>()
                    .eq(User::getUsername, loginRequest.getUsername())
            );

            // 生成JWT token
            String token = jwtUtils.generateToken(user.getUsername(), user.getId());

            // 返回token和用户信息
            return Result.success(new LoginResponse(token, user.getUsername()));
        } catch (BadCredentialsException e) {
            return Result.error(401, "用户名或密码错误");
        } catch (Exception e) {
            return Result.error("登录失败：" + e.getMessage());
        }
    }
} 