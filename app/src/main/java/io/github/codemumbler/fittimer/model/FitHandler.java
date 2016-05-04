package io.github.codemumbler.fittimer.model;

public abstract class FitHandler {

    public static final int MILLISECOND_THRESHOLD = 99;
    private Callback finishCallback;
    private Callback tickCallback;
    private long targetTime = -1;
    private Callback targetTimeCallback;
    private boolean targetComplete = false;

    public void finish() {
        if (finishCallback != null) {
            finishCallback.execute(0);
        }
    }

    public void tick(long remainingTime) {
        if (tickCallback != null) {
            tickCallback.execute(remainingTime);
        }
        if (targetTimeCallback != null && (targetTime + MILLISECOND_THRESHOLD > remainingTime)
                && !targetComplete) {
            targetComplete = true;
            targetTimeCallback.execute(remainingTime);
        }
    }

    public void onFinish(Callback callback) {
        finishCallback = callback;
    }

    public void onTargetTime(final long targetTime, final Callback callback) {
        this.targetTime = targetTime;
        this.targetTimeCallback = callback;
    }

    public void onTick(Callback callback) {
        tickCallback = callback;
    }

    public interface Callback {

        void execute(long remainingTime);
    }
}
