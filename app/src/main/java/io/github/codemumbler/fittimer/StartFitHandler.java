package io.github.codemumbler.fittimer;

import android.os.Handler;
import android.os.Message;

import io.github.codemumbler.fittimer.model.FitHandler;

public class StartFitHandler extends FitHandler {

    public StartFitHandler(final SessionRunner sessionRunner) {
        sessionRunner.getContentDisplay().setText(R.string.get_ready);
        final Handler tickHandler = new Handler() {
            public void handleMessage(Message msg) {
                Long remainingTime = (Long) msg.obj;
                sessionRunner.updateTimerText(remainingTime);
            }
        };
        final Handler finishHandler = new Handler() {
            public void handleMessage(Message msg) {
                sessionRunner.next();
                sessionRunner.endPoseSound();
            }
        };
        onTick(new Callback() {
            @Override
            public void execute(long remainingTime) {
                tickHandler.obtainMessage(1, remainingTime).sendToTarget();
            }
        });
        onFinish(new Callback() {
            @Override
            public void execute(long remainingTime) {
                finishHandler.obtainMessage(2).sendToTarget();
            }
        });
        for (int i=1000; i<3001; i+=1000) {
            addOnTargetTime(i, new Callback() {
                @Override
                public void execute(long remainingTime) {
                    sessionRunner.tickSound();
                }
            });
        }
    }
}
