package cn.nianzx.aop;

import java.lang.annotation.*;

/**
 * 自定义注解
 * Created by nianzx on 2019/1/25.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface MyAnnotation {
}
