package cn.com.servyou.annotation;

import java.lang.annotation.*;

/**
 * 控制器注解
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    String value();
}
