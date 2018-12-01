package cn.com.servyou.annotation;

import java.lang.annotation.*;

/**
 * <pre>类名: RequestMapping</pre>
 * <pre>描述: TODO</pre>
 * <pre>版权: 税友软件集团股份有限公司</pre>
 * <pre>日期: 2018年11月30日 19:03</pre>
 * <pre>作者: yuhd</pre>
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    String value();

}
