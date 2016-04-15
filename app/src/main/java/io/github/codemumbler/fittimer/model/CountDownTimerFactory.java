package io.github.codemumbler.fittimer.model;

import android.os.CountDownTimer;

import io.github.codemumbler.fittimer.FitCountDownTimer;
import io.github.codemumbler.fittimer.SessionRunner;

public class CountDownTimerFactory {

    public CountDownTimer createCountDownTimer(SessionRunner session, int duration) {
        return new FitCountDownTimer(duration * 1000, session);
    }
}
