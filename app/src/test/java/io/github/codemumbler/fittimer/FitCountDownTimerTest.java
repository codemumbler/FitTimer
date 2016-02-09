package io.github.codemumbler.fittimer;

import android.content.Context;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import io.github.codemumbler.fittimer.model.PoseQueue;
import io.github.codemumbler.fittimer.model.Session;

@RunWith(MockitoJUnitRunner.class)
public class FitCountDownTimerTest {

    @Mock
    Context mockContext;
    @Mock
    TextView timer;
    @Mock
    TextView content;
    @Mock
    TextToSpeech textToSpeech;
    @Mock
    CountDownTimer countDownTimer;

    private FitCountDownTimer fitCountDownTimer;


    private SessionRunner session;

    @Before
    public void setUp() {
        PoseQueue poseQueue = new PoseQueue();
        poseQueue.add("pose 1");
        session = new SessionRunner(new Session(poseQueue));
        session.setTimerDisplay(timer);
        session.setContentDisplay(content);
        session.setTextToSpeech(new TextToSpeechWrapper(session, mockContext));
        fitCountDownTimer = new FitCountDownTimer(1000, session);
        session.setTimerFactory(new MockFitCountDownTimerFactory(countDownTimer));
    }

    @Test
    public void tickUpdatesDisplayValue() throws Exception {
        fitCountDownTimer.onTick(700);
        Mockito.verify(timer).setText("0.7");
    }

    @Test
    public void finishCanCompleteSession() throws Exception {
        fitCountDownTimer.onFinish();
        Assert.assertTrue(session.complete());
    }
}