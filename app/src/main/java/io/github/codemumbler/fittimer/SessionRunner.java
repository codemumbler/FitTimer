package io.github.codemumbler.fittimer;

import android.content.Context;
import android.widget.TextView;

import io.github.codemumbler.fittimer.model.FitCountDownTimer;
import io.github.codemumbler.fittimer.model.Session;

public class SessionRunner {

    private Session session;
    private TextView contentDisplay;
    private TextView timerDisplay;
    private FitCountDownTimer timer;
    private boolean isPaused = true;
    private Callback completionCallback;

    private final TextToSpeechWrapper textToSpeech;

    SessionRunner(final Session session, final Context context, final SessionRunner.Callback callback) {
        this.session = session;
        this.textToSpeech = new TextToSpeechWrapper(callback, context);
    }

    public void setContentDisplay(TextView contentDisplay) {
        this.contentDisplay = contentDisplay;
    }

    public TextView getContentDisplay() {
        return contentDisplay;
    }

    public void setTimerDisplay(TextView timerDisplay) {
        this.timerDisplay = timerDisplay;
    }

    public TextView getTimerDisplay() {
        return timerDisplay;
    }

    public void updateTimerText(Long remainingTime) {
        getTimerDisplay().setText(String.format("%.1f", remainingTime/1000.0));
    }

    public void next() {
        if (session.next()) {
            startPose();
        } else {
            complete();
        }
    }

    private void startPose() {
        if (timer != null) {
            timer.cancel();
        }
        getContentDisplay().setText(session.getCurrentPose().getName());
        textToSpeech.speak(session.getCurrentPose().getName());
        timer = new FitCountDownTimer(session.getCurrentPose().getDuration(),
                new AndroidFitHandler(this));
        timer.start();
        isPaused = false;
    }

    public boolean complete() {
        textToSpeech.shutdown();
        if (this.completionCallback != null) {
            completionCallback.execute();
        }
        return session.complete();
    }

    public void start() {
        next();
    }

    private void pause() {
        timer.pause();
        isPaused = true;
    }

    private void resume() {
        if (timer != null) {
            timer.start();
            isPaused = false;
        }
    }

    public void pausePlay() {
        if (!isPaused) {
            pause();
        } else {
            resume();
        }
    }

    public void onComplete(Callback callback) {
        completionCallback = callback;
    }

    public void prev() {
        if (session.prev()) {
            startPose();
        }
    }

    public interface Callback {

        void execute();
    }
}
