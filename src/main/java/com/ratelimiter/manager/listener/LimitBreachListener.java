package com.ratelimiter.manager.listener;

/**
 * Listen if Rate limit is breached.
 */
public interface LimitBreachListener {
    /**
     * Implement this.
     */
    public void limitBreachListener();
}
