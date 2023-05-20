package com.example.infra;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySessionLifecycleListener implements HttpSessionListener {

    private static Logger logger = LoggerFactory.getLogger(MySessionLifecycleListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        logger.info("session created: {}", event.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        logger.info("session destroyed: {}", event.getSession().getId());
    }

}
