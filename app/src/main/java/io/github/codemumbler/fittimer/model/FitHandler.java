package io.github.codemumbler.fittimer.model;

public abstract class FitHandler {

    private Callback tickCallback;
    private Callback finishCallback;

    public void onTick(Callback callback) {
        tickCallback = callback;
    }

    public void tick(double remainingTime) {
        if (tickCallback != null) {
            tickCallback.execute(remainingTime);
        }
    }

    public void finish() {
        if (finishCallback != null) {
            finishCallback.execute(0);
        }
    }

    public void onFinish(Callback callback) {
        finishCallback = callback;
    }

    public interface Callback {

        void execute(double remainingTime);
    }
}
