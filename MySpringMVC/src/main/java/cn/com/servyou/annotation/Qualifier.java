package cn.com.servyou.annotation;

import java.lang.annotation.*;

/**
 * 限定符注解
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Qualifier {

    String value();

}
