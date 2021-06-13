package com.ratelimiter.manager;

import com.ratelimiter.manager.impl.SmoothRateLimitImpl;
import com.ratelimiter.manager.listener.LimitBreachListener;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class RateLimiterManager {
    private static RateLimiterManager instance = null;
    private ConcurrentHashMap<String, SmoothRateLimitImpl> rateLimitMap = new ConcurrentHashMap<>();
    private ExecutorService threadPool = Executors.newFixedThreadPool(2);

    private RateLimiterManager() {

    }

    public static RateLimiterManager getInstance() {
        if (instance == null) {
            synchronized (RateLimiterManager.class) {
                if (instance == null)
                    return new RateLimiterManager();
            }
        }
        return instance;
    }

    public void provisionRateLimit(SmoothRateLimitImpl executor, String instanceId, LimitBreachListener listener) {
        executor.setListener(listener);
        rateLimitMap.put(instanceId, executor);
    }

    public void isAllowed(String instanceId) {
        rateLimitMap.get(instanceId).evaluate();
    }

    public void removeInstance(String instanceId) {
        rateLimitMap.remove(instanceId);
    }

    public ExecutorService getThreadPool() {
        return threadPool;
    }
}
