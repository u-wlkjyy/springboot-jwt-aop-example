package com.soft.permissondemo.annotation;

import java.lang.annotation.*;

/**
 * 权限校验注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HasPermissions {
    /**
     * 需要校验的权限标识
     */
    String value() default "";
    
    /**
     * 验证模式：AND | OR
     */
    Logical logical() default Logical.AND;
} 