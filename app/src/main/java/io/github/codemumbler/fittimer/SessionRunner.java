package io.github.codemumbler.fittimer;

import android.content.Context;
import android.widget.TextView;

import io.github.codemumbler.fittimer.model.FitCountDownTimer;
import io.github.codemumbler.fittimer.model.Session;

public class SessionRunner {

    private Session session;
    private TextView contentDisplay;
    private TextView timerDisplay;

    private final TextToSpeechWrapper textToSpeech;

    SessionRunner(final Session session, final Context context) {
        this.session = session;
        this.textToSpeech = new TextToSpeechWrapper(this, context);
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

    public void updateTimerText(Double remainingTimeInSeconds) {
        getTimerDisplay().setText(String.format("%.1f", remainingTimeInSeconds));
    }

    public void next() {
        if (session.next()) {
            getContentDisplay().setText(session.getCurrentPose().getName());
            textToSpeech.speak(session.getCurrentPose().getName());
            FitCountDownTimer timer = new FitCountDownTimer(session.getCurrentPose().getDuration(),
                    new AndroidFitHandler(this));
            timer.start();
        } else {
            complete();
        }
    }

    public boolean complete() {
        textToSpeech.shutdown();
        return session.complete();
    }
}
