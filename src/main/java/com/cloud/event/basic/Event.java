package com.cloud.event.basic;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2018/1/30 20:24
 * @description
 */
public class Event {
    private String destinationService;
    private String eventName;
    private Map<String, String> eventArguments;


    public Event addEventArgument(String name, String value) {
        if(Objects.isNull(this.eventArguments)) {
            this.eventArguments = new HashMap<>(16);
        }

        this.eventArguments.put(name, value);
        return this;
    }

    public String getDestinationService() {
        return destinationService;
    }

    public Event setDestinationService(String destinationService) {
        this.destinationService = destinationService;
        return this;
    }

    public String getEventName() {
        return eventName;
    }

    public Event setEventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    public Map<String, String> getEventArguments() {
        return eventArguments;
    }

    public Event setEventArguments(Map<String, String> eventArguments) {
        this.eventArguments = eventArguments;
        return this;
    }


}
