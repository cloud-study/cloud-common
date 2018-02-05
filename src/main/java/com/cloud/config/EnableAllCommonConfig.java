package com.cloud.config;

import com.cloud.datasource.DynamicDataSourceRegister;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2018/2/1 20:39
 * @description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@RemoteApplicationEventScan(basePackages = "com.cloud.event.basic")
@Import({DynamicDataSourceRegister.class})
public @interface EnableAllCommonConfig {
}
