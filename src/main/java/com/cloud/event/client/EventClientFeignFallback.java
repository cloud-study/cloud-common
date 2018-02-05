package com.cloud.event.client;

import com.cloud.event.basic.Event;
import org.springframework.stereotype.Component;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2018/1/31 9:59
 * @description
 */
@Component
public class EventClientFeignFallback  implements EventClientFeign {
    @Override
    public boolean publishPublicEvent(Event event) {
        throw new RuntimeException("事件触发失败:" + event);
    }
}
