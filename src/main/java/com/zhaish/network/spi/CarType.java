package com.zhaish.network.spi;

import java.lang.annotation.*;

/**
 * @datetime:2021/3/29 14:30
 * @author: zhaish
 * @desc:
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CarType {
    String value() default "";
}
