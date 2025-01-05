package com.soft.permissondemo.aspect;

import com.soft.permissondemo.annotation.HasPermissions;
import com.soft.permissondemo.annotation.Logical;
import com.soft.permissondemo.service.IUserService;
import com.soft.permissondemo.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * 权限校验切面
 */
@Aspect
@Component
public class PermissionAspect {

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Around("@annotation(com.soft.permissondemo.annotation.HasPermissions)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 获取当前请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        
        // 从请求头获取token
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        if (token == null || !jwtUtils.validateToken(token)) {
            throw new RuntimeException("无效的token");
        }

        // 获取用户ID
        Integer userId = jwtUtils.getUserIdFromToken(token);
        
        // 获取方法签名
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        
        // 获取注解
        HasPermissions hasPermissions = method.getAnnotation(HasPermissions.class);
        
        // 获取权限标识
        String permission = hasPermissions.value();
        Logical logical = hasPermissions.logical();
        
        // 获取用户权限
        Set<String> permissions = userService.getUserPermissions(userId);
        
        // 判断是否有权限
        boolean hasPermission = false;
        if (logical == Logical.OR) {
            // 有任一权限即可
            String[] permissionArray = permission.split(",");
            for (String perm : permissionArray) {
                if (permissions.contains(perm.trim())) {
                    hasPermission = true;
                    break;
                }
            }
        } else {
            // 需要所有权限
            String[] permissionArray = permission.split(",");
            hasPermission = true;
            for (String perm : permissionArray) {
                if (!permissions.contains(perm.trim())) {
                    hasPermission = false;
                    break;
                }
            }
        }
        
        if (!hasPermission) {
            throw new RuntimeException("没有操作权限");
        }
        
        // 有权限则执行原方法
        return point.proceed();
    }
} 