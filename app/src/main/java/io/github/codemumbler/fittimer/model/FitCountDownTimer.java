package io.github.codemumbler.fittimer.model;

import java.util.Timer;
import java.util.TimerTask;

public class FitCountDownTimer {

    public static final int TICK_INTERVAL = 100;
    private Timer timer;
    private long totalTime;
    private final FitHandler handler;
    private FitCountDownTimerTask task;
    private final Clock clock;

    /**
     * @param totalTime the length of time the timer will run in seconds
     * @param handler
     */
    public FitCountDownTimer(final long totalTime, final FitHandler handler) {
        this(totalTime, handler, new SystemClock());
    }

    public FitCountDownTimer(final long totalTime, final FitHandler handler, final Clock clock) {
        this.totalTime = totalTime;
        this.handler = handler;
        timer = new Timer();
        this.clock = clock;
    }

    /**
     * Start the count down
     */
    public void start() {
        task = new FitCountDownTimerTask();
        timer.scheduleAtFixedRate(task, 0, TICK_INTERVAL);
    }

    public void pause() {
        if (task != null) {
            task.cancel();
            totalTime = task.getRemainingTime();
            task = null;
        }
    }

    public void cancel() {
        if (task != null) {
            task.cancel();
            totalTime = 0;
            task = null;
        }
    }

    private class FitCountDownTimerTask extends TimerTask {

        private long startTime = clock.currentTimeMillis();
        private long endTime = startTime + totalTime;
        private long remainingTime = totalTime;

        @Override
        public void run() {
            remainingTime = endTime - clock.currentTimeMillis();
            if (remainingTime < 0) {
                handler.tick(0);
                handler.finish();
                this.cancel();
                return;
            }
            handler.tick(remainingTime);
        }

        public long getRemainingTime() {
            return remainingTime;
        }
    }
}
