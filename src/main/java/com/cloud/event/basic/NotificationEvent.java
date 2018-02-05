package com.cloud.event.basic;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2018/1/30 20:21
 * @description
 */
public class NotificationEvent extends RemoteApplicationEvent {
    private String eventName;

    private Map<String, String> eventArguments;

    public NotificationEvent() {
        super();
    }

    public NotificationEvent(final Object source, final String originService, final String destinationService) {
        super(source, originService, destinationService);
        this.setEventArguments(new HashMap<>());
    }

    public String getEventName() {
        return eventName;
    }

    public NotificationEvent setEventName(final String eventName) {
        this.eventName = eventName;
        return this;
    }

    public Map<String, String> getEventArguments() {
        return eventArguments;
    }

    public NotificationEvent setEventArguments(final Map<String, String> eventArguments) {
        this.eventArguments = eventArguments;
        return this;
    }

    public String getArgument(String name) {
        return eventArguments.get(name);
    }

    public NotificationEvent arg(final String name, final String value) {
        this.getEventArguments().put(name, value);
        return this;
    }
}
