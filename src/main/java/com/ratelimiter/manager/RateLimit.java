package com.ratelimiter.manager;

import com.ratelimiter.manager.listener.LimitBreachListener;
import com.ratelimiter.manager.listener.WorkerThread;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class RateLimit {
    protected final ReentrantReadWriteLock lock;
    protected final ReentrantReadWriteLock.WriteLock wLock;
    protected long threshold;
    private LimitBreachListener listener;

    public RateLimit() {
        lock = new ReentrantReadWriteLock();
        wLock = lock.writeLock();
    }

    public abstract void evaluate();

    public void callListener() {
        RateLimiterManager.getInstance().getThreadPool().execute(new WorkerThread(listener));
    }

    public void setListener(LimitBreachListener listener) {
        this.listener = listener;
    }

    public void build(long threshold) {
        this.threshold = threshold;
    }
}
