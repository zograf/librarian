package com.librarian.events;

import org.springframework.context.ApplicationEvent;

public class TrendingEvent extends ApplicationEvent {

    public TrendingEvent(Object source) {
        super(source);
    }

}
