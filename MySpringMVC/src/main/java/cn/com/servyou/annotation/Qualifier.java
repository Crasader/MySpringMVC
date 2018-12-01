package cn.com.servyou.annotation;

import java.lang.annotation.*;

/**
 * <pre>类名: Qualifier</pre>
 * <pre>描述: 限定符注解</pre>
 * <pre>版权: 税友软件集团股份有限公司</pre>
 * <pre>日期: 2018年11月30日 18:54</pre>
 * <pre>作者: yuhd</pre>
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Qualifier {

    String value();

}
