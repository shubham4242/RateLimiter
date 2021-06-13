package com.ratelimiter.manager.impl;

import com.ratelimiter.manager.RateLimit;
    
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SmoothRateLimitImpl extends RateLimit {
    private final static Logger LOGGER = Logger.getLogger(SmoothRateLimitImpl.class.getName());
    private Queue<Long> transaction = new LinkedList<>();

    public void evaluate() {
        wLock.lock();
        long currentTime = System.currentTimeMillis();
        while (transaction.peek() > (currentTime - 1000)) transaction.poll();
        if (transaction.size() > threshold) {
            LOGGER.log(Level.SEVERE, "Threshold limit exceed");
            callListener();
        } else {
            transaction.add(currentTime);
        }
        wLock.unlock();
    }
}
