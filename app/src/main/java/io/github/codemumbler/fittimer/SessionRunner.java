package io.github.codemumbler.fittimer;

import android.widget.TextView;

import io.github.codemumbler.fittimer.model.Session;

public class SessionRunner {

    private Session session;
    private TextView contentDisplay;
    private TextView timerDisplay;
    private TextToSpeechWrapper textToSpeech;

    SessionRunner(final Session session) {
        this.session = session;
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

    public void setTextToSpeech(TextToSpeechWrapper textToSpeech) {
        this.textToSpeech = textToSpeech;
    }

    public TextToSpeechWrapper getTextToSpeech() {
        return textToSpeech;
    }

    public void updateTimerText(String timerText) {
        getTimerDisplay().setText(timerText);
    }

    public void next() {
        if (session.next()) {
            getContentDisplay().setText(session.poseName());
            getTextToSpeech().speak(session.poseName());
            new FitCountDownTimer(4000, this).start();
        }
    }

    public boolean complete() {
        getTextToSpeech().shutdown();
        return session.complete();
    }
}
