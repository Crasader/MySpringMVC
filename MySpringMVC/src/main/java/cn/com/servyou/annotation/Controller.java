package cn.com.servyou.annotation;

import java.lang.annotation.*;

/**
 * <pre>类名: Controller</pre>
 * <pre>描述: 控制器注解</pre>
 * <pre>版权: 税友软件集团股份有限公司</pre>
 * <pre>日期: 2018年11月30日 18:49</pre>
 * <pre>作者: yuhd</pre>
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    String value();
}
