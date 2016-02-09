package io.github.codemumbler.fittimer;

import android.os.CountDownTimer;

import io.github.codemumbler.fittimer.model.Session;

public class FitCountDownTimer extends CountDownTimer {
    private final SessionRunner sessionRunner;

    /**
     * @param millisInFuture The number of millis in the future from the call
     *                       to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                       is called.
     * @param sessionRunner
     */
    public FitCountDownTimer(long millisInFuture, final SessionRunner sessionRunner) {
        super(millisInFuture, 100);
        this.sessionRunner = sessionRunner;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        sessionRunner.updateTimerText(String.format("%.1f", (millisUntilFinished / 1000.0)));
    }

    @Override
    public void onFinish() {
        sessionRunner.next();
    }
}
