package io.github.codemumbler.fittimer.model;

import java.util.SortedSet;
import java.util.TreeSet;

public abstract class FitHandler {

    private static final int MILLISECOND_THRESHOLD = 99;
    private static final int IGNORE_MILLISECOND_THRESHOLD = 300;

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
        if (!targetTimeEvents.isEmpty()) {
            TargetTimeEvent event = targetTimeEvents.first();
            while (event.getTargetTime() - IGNORE_MILLISECOND_THRESHOLD > remainingTime) {
                targetTimeEvents.remove(event);
                if (!targetTimeEvents.isEmpty()) {
                    event = targetTimeEvents.first();
                } else {
                    event = null;
                    break;
                }
            }
            if (event != null
                    && (event.getTargetTime() + MILLISECOND_THRESHOLD > remainingTime)
                    && !event.isTargetComplete()) {
                event.execute(remainingTime);
                targetTimeEvents.remove(event);
            }
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
