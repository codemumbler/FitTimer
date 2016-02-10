package io.github.codemumbler.fittimer;

import android.os.CountDownTimer;

public class CountDownTimerFactory {

    public CountDownTimer createCountDownTimer(SessionRunner session) {
        return new FitCountDownTimer(4000, session);
    }
}
