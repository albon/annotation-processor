package com.albon.arith.annotation.procecssor;

import java.lang.annotation.*;

/**
 * @author albon
 *         Date : 17-1-19
 *         Time: 上午11:02
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoParse {
    // 一个普通参数 key
    String key();
}
