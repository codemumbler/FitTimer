package io.github.codemumbler.fittimer.model;

import java.util.Timer;
import java.util.TimerTask;

public class FitCountDownTimer {

    public static final int TICK_INTERVAL = 100;
    private Timer timer;
    private long totalTime;
    private final FitHandler handler;
    private TimerTask task;
    private long remainingTime;

    /**
     * @param totalTimeInSeconds the length of time the timer will run in seconds
     * @param handler
     */
    public FitCountDownTimer(final double totalTimeInSeconds, final FitHandler handler) {
        this.totalTime = (long) (totalTimeInSeconds * 1000);
        this.handler = handler;
        timer = new Timer();
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
            totalTime = remainingTime;
            task = null;
        }
    }

    private class FitCountDownTimerTask extends TimerTask {

        long startTime = System.currentTimeMillis();
        long endTime = startTime + totalTime;

        @Override
        public void run() {
            remainingTime = endTime - System.currentTimeMillis();
            if (remainingTime < 0) {
                handler.tick(0);
                handler.finish();
                this.cancel();
                return;
            }
            handler.tick(remainingTime/1000.0);
        }
    }
}
