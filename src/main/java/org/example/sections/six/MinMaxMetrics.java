package org.example.sections.six;

public class MinMaxMetrics {
    // Add all necessary member variables
    volatile long min;
    volatile long max;
    final Object lock;

    public MinMaxMetrics() {
        this.min = Long.MIN_VALUE;
        this.max = Long.MAX_VALUE;
        lock = new Object();
    }

    public void addSample(long newSample) {
        synchronized (lock) {
            min = Math.min(newSample, min);
            max = Math.max(newSample, max);
        }
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }
}
