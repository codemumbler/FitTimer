package io.github.codemumbler.fittimer;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Handler;
import android.os.Message;

import io.github.codemumbler.fittimer.model.FitHandler;

public class StartFitHandler extends FitHandler {

    public StartFitHandler(final SessionRunner sessionRunner) {
        sessionRunner.getContentDisplay().setText("Get ready...");
        final Handler tickHandler = new Handler() {
            public void handleMessage(Message msg) {
                Long remainingTime = (Long) msg.obj;
                sessionRunner.updateTimerText(remainingTime);
            }
        };
        final Handler finishHandler = new Handler() {
            public void handleMessage(Message msg) {
                sessionRunner.next();
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
                try {
                    final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
                    tg.startTone(ToneGenerator.TONE_PROP_BEEP2);
                    tg.release();
                } catch (Exception e) {
                    //ignore missed tone
                }
            }
        });
        for (int i=1000; i<3001; i+=1000) {
            addOnTargetTime(i, new Callback() {
                @Override
                public void execute(long remainingTime) {
                    try {
                        final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
                        tg.startTone(ToneGenerator.TONE_PROP_BEEP);
                        tg.release();
                    } catch (Exception e) {
                        //ignore missed tone
                    }
                }
            });
        }
    }
}
