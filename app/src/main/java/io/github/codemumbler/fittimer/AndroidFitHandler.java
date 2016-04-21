package io.github.codemumbler.fittimer;

import android.os.Handler;
import android.os.Message;

import io.github.codemumbler.fittimer.model.FitHandler;

class AndroidFitHandler extends FitHandler {

    private final Handler tickHandler;
    private final Handler finishHandler;

    AndroidFitHandler(final SessionRunner session) {
        this.tickHandler = new Handler() {
            public void handleMessage(Message msg) {
                Double remainingTime = (Double) msg.obj;
                session.updateTimerText(remainingTime);
            }
        };
        this.finishHandler = new Handler() {
            public void handleMessage(Message msg) {
                session.next();
            }
        };
        onTick(new Callback() {
            @Override
            public void execute(double remainingTime) {
                tickHandler.obtainMessage(1, remainingTime).sendToTarget();
            }
        });
        onFinish(new Callback() {
            @Override
            public void execute(double remainingTime) {
                finishHandler.obtainMessage(2).sendToTarget();
            }
        });
    }
}
