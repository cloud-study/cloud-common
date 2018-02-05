package com.cloud.event.client;

import com.cloud.event.basic.Event;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2018/1/31 9:56
 * @description
 */
@FeignClient(name = "config-server", fallback = EventClientFeignFallback.class)
public interface EventClientFeign {

    @RequestMapping(path = {"/api/global/public/event"}, method = {RequestMethod.POST})
    boolean publishPublicEvent(@RequestBody Event event);
}
