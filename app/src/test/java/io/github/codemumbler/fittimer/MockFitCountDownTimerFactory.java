package io.github.codemumbler.fittimer;

import android.os.CountDownTimer;

import io.github.codemumbler.fittimer.model.Session;

public class MockFitCountDownTimerFactory extends CountDownTimerFactory {

    private CountDownTimer countDownTimer;

    public MockFitCountDownTimerFactory(CountDownTimer countDownTimer) {
        this.countDownTimer = countDownTimer;
    }

    @Override
    public CountDownTimer createCountDownTimer(SessionRunner session) {
        return countDownTimer;
    }
}
