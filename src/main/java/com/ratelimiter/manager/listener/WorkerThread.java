package com.ratelimiter.manager.listener;

import com.ratelimiter.manager.listener.LimitBreachListener;

public class WorkerThread implements Runnable {
    private LimitBreachListener listener;

    public WorkerThread(LimitBreachListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        this.listener.limitBreachListener();
    }
}
