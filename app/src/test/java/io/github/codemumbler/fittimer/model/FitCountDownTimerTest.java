package io.github.codemumbler.fittimer.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class FitCountDownTimerTest {

    private FitHandler fitHandler;
    private int callbackCount;
    private FitCountDownTimer fitCountDownTimer;
    private double lastRemainingTime;
    private boolean completeAfterFirstTick;

    @Before
    public void setUp() {
        callbackCount = 0;
        completeAfterFirstTick = false;
        fitHandler = new MockFitHandler();
        fitCountDownTimer = new FitCountDownTimer(0.1, fitHandler);
        FitHandler.Callback handler = new FitHandler.Callback() {
            @Override
            public void execute(double remainingTime) {
                lastRemainingTime = remainingTime;
                callbackCount++;
                if (completeAfterFirstTick) {
                    fitCountDownTimer.pause();
                }
            }
        };
        lastRemainingTime = -1;
        fitHandler.onTick(handler);
        fitHandler.onFinish(handler);
    }

    @Test
    public void tickUpdatesDisplayValue() throws Exception {
        fitCountDownTimer.start();
        completeAfterTick(1);
        Assert.assertEquals(0.1, lastRemainingTime/1000.0, 0.1);
    }

    private void completeAfterTick(int tickCount) {
        if (tickCount == 1) {
            completeAfterFirstTick = true;
        }
        long startTime = System.currentTimeMillis();
        while (callbackCount < tickCount) {
            Thread.yield();
            if (System.currentTimeMillis() - startTime > 1000) {
                Assert.fail("test has failed");
            }
        }
    }

    @Test
    public void onFinishRanUntilCompletion() throws Exception {
        fitCountDownTimer.start();
        completeAfterTick(3);
        Assert.assertEquals(0.0, lastRemainingTime, 0);
    }

    @Test
    public void onFinishCallback() throws Exception {
        fitCountDownTimer.start();
        completeAfterTick(3);
        // 3 or 4 ticks are acceptable
        Assert.assertTrue(callbackCount >= 3);
    }

    @Test
    public void pause() throws Exception {
        fitCountDownTimer.start();
        fitCountDownTimer.pause();
        Thread.yield();
        Assert.assertEquals(-1.0, lastRemainingTime, 0.0);
    }

    @Test
    public void resume() throws Exception {

        fitCountDownTimer = new FitCountDownTimer(1, fitHandler);
        fitCountDownTimer.start();
        fitCountDownTimer.pause();
        Thread.yield();
        fitCountDownTimer.start();
        completeAfterTick(3);
        Assert.assertEquals(3, callbackCount);
    }
}