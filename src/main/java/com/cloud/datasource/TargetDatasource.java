package com.cloud.datasource;


import java.lang.annotation.*;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2018/2/1 9:40
 * @description
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDatasource {
    String name();
}