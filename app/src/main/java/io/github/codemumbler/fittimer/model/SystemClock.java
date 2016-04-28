package io.github.codemumbler.fittimer.model;

class SystemClock implements Clock {

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
