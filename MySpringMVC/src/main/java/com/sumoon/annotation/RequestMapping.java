package com.sumoon.annotation;

import java.lang.annotation.*;

/**
 * 请求映射注解
 * 作用于类和方法上
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    String value();

}
