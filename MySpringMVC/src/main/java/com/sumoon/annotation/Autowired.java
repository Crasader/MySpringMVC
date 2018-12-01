package com.sumoon.annotation;

import java.lang.annotation.*;

/**
 * 自动注入注解
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
    String value() default "";
}
