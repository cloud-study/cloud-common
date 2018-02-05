package com.cloud.event.basic;

import org.springframework.context.event.EventListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2018/1/30 20:19
 * @description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@EventListener({NotificationEvent.class})
public @interface Topic {
    String name();
}
