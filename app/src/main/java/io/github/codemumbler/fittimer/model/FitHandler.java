package io.github.codemumbler.fittimer.model;

import java.util.SortedSet;
import java.util.TreeSet;

import io.github.codemumbler.fittimer.SessionRunner;

public abstract class FitHandler {

    public static final int MILLISECOND_THRESHOLD = 99;
    private Callback finishCallback;
    private Callback tickCallback;
    private SortedSet<TargetTimeEvent> targetTimeEvents = new TreeSet<>();

    public void finish() {
        if (finishCallback != null) {
            finishCallback.execute(0);
        }
    }

    public void tick(long remainingTime) {
        if (tickCallback != null) {
            tickCallback.execute(remainingTime);
        }
        if (!targetTimeEvents.isEmpty() &&
                (targetTimeEvents.first().getTargetTime() + MILLISECOND_THRESHOLD > remainingTime)
                && !targetTimeEvents.first().isTargetComplete()) {
            targetTimeEvents.first().execute(remainingTime);
            targetTimeEvents.remove(targetTimeEvents.first());
        }
    }

    public void onFinish(Callback callback) {
        finishCallback = callback;
    }

    public void addOnTargetTime(final long targetTime, final Callback callback) {
        targetTimeEvents.add(new TargetTimeEvent(targetTime, callback));
    }

    public void onTick(Callback callback) {
        tickCallback = callback;
    }

    public interface Callback {

        void execute(long remainingTime);
    }

    private static class TargetTimeEvent implements Comparable {
        private final long targetTime;
        private final Callback targetTimeCallback;
        private boolean targetComplete = false;

        public TargetTimeEvent(final long targetTime, final Callback targetCallback) {
            this.targetTime = targetTime;
            this.targetTimeCallback = targetCallback;
        }

        public long getTargetTime() {
            return targetTime;
        }

        public void execute(final long remainingTime) {
            targetTimeCallback.execute(remainingTime);
        }

        public boolean isTargetComplete() {
            return targetComplete;
        }

        @Override
        public int compareTo(Object another) {
            if (!(another instanceof TargetTimeEvent)) {
                return -1;
            }
            TargetTimeEvent anotherTargetTime = (TargetTimeEvent) another;
            return (int) (anotherTargetTime.getTargetTime() - targetTime);
        }
    }
}
