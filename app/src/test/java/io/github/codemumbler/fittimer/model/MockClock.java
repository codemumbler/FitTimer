package io.github.codemumbler.fittimer.model;

public class MockClock implements Clock {

    private long time = 0;

    public void tick() {
        this.time += 101;
    }

    @Override
    public long currentTimeMillis() {
        return time;
    }
}
