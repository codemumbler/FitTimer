package io.github.codemumbler.fittimer.model;

public class MockClock implements Clock {

    private long time = 0;
    private long tickSize = 101;

    public void tick() {
        this.time += tickSize;
    }

    public void setTickSize(final long tickSize) {
        this.tickSize = tickSize;
    }

    @Override
    public long currentTimeMillis() {
        return time;
    }
}
