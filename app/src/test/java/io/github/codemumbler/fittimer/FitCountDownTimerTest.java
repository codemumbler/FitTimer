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

import java.util.ArrayList;
import java.util.List;

import io.github.codemumbler.fittimer.model.Pose;
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
        List<Pose> poseQueue = new ArrayList<>();
        poseQueue.add(new Pose("pose 1"));
        session = new SessionRunner(new Session(poseQueue), mockContext);
        session.setTimerDisplay(timer);
        session.setContentDisplay(content);
        session.setTimerFactory(new MockFitCountDownTimerFactory(countDownTimer));
        fitCountDownTimer = new FitCountDownTimer(1000, session);
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