package com.librarian.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.librarian.service.TrendingService;

@Component
public class TrendingEventListener implements ApplicationListener<TrendingEvent> {

    @Autowired
    private TrendingService service;

    @Override
    public void onApplicationEvent(TrendingEvent event) {
        service.updateTrendingBooks();
    }
    
}
