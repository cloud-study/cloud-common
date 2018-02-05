package com.cloud.event.basic;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2018/2/1 9:16
 * @description
 */
@Component
@Aspect
@Order(-2)
public class EventAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventAspect.class);

    public EventAspect() {
    }

    @Around("@annotation(topic)")
    public Object eventCheck(ProceedingJoinPoint joinPoint, Topic topic) throws Throwable {
        Object[] args = joinPoint.getArgs();
        NotificationEvent event = null;
        Object[] throwable = args;
        int var6 = args.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Object arg = throwable[var7];
            if(arg instanceof NotificationEvent) {
                event = (NotificationEvent)arg;
            }
        }

        if(!Objects.isNull(event) && Objects.equals(event.getEventName(), topic.name())) {
            LOGGER.info("Event {} is proceeding, argument: {}", event.getEventName(), event.getEventArguments());

            try {
                return joinPoint.proceed();
            } catch (Throwable var9) {
                LOGGER.error("Event " + event.getEventName() + " failed.", var9);
                throw var9;
            }
        } else {
            return null;
        }
    }
}
