package io.github.codemumbler.fittimer.model;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class FitCountDownTimerTest {

    public static final int TOTAL_TIME = 10000;
    private FitHandler fitHandler;
    private FitCountDownTimer fitCountDownTimer;
    private MockClock clock;
    private boolean done = false;

    @Before
    public void setUp() {
        clock = new MockClock();
        fitHandler = new MockFitHandler();
        fitCountDownTimer = new FitCountDownTimer(TOTAL_TIME, fitHandler, clock);
    }

    @Test
    public void tickUpdatesDisplayValue() throws Exception {
        fitCountDownTimer.start();
        clock.tick();
        assertRemainingTimeAfterTick(9899);
    }



    @Test
    public void onFinishRanUntilCompletion() throws Exception {
        fitCountDownTimer.start();
        assertRemainingTimeAtFinish(0);

    }

    @Test
    public void pause() throws Exception {
        fitCountDownTimer.start();
        fitCountDownTimer.pause();
        assertTickCallbackIsNotCalled();
    }

    @Test
    public void resume() throws Exception {
        fitCountDownTimer.start();
        fitCountDownTimer.pause();
        clock.tick();
        fitCountDownTimer.start();
        clock.tick();
        assertRemainingTimeAfterTick(9899);
    }

    @Test
    public void onTargetTimeEvent() throws Exception {
        fitCountDownTimer.start();
        FitHandler.Callback handler = new FitHandler.Callback() {
            @Override
            public void execute(long remainingTime) {
                Assert.assertEquals(9, (long) Math.ceil(remainingTime/1000.0));
                done = true;
            }
        };
        fitHandler.onTargetTime(9000, handler);
        for (int i=0; i < 13; i++) {
            clock.tick();
            Thread.sleep(10);
        }
        Assert.assertTrue(done);
    }

    @Test
    public void onTargetTimeEventCalledOnce() throws Exception {
        fitCountDownTimer.start();
        FitHandler.Callback handler = new FitHandler.Callback() {
            @Override
            public void execute(long remainingTime) {
                Assert.assertEquals(9, (long) Math.ceil(remainingTime/1000.0));
                done = true;
            }
        };
        fitHandler.onTargetTime(9000, handler);
        for (int i = 0; i < 30; i++) {
            clock.tick();
            Thread.sleep(10);
        }
        Assert.assertTrue(done);
    }

    private void assertRemainingTimeAfterTick(final int expectedTime) {
        FitHandler.Callback handler = new FitHandler.Callback() {
            @Override
            public void execute(long remainingTime) {
                Assert.assertEquals(expectedTime, remainingTime);
                done = true;
            }
        };
        fitHandler.onTick(handler);
        while (!done) {
            Thread.yield();
        }
    }

    private void assertTickCallbackIsNotCalled() throws Exception {
        FitHandler.Callback handler = new FitHandler.Callback() {
            @Override
            public void execute(long remainingTime) {
                Assert.fail("Should not be called");
            }
        };
        fitHandler.onTick(handler);
        // give TimerTask time to be woken up
        for (int wait = 0; wait < 10; wait++) {
            clock.tick();
            Thread.sleep(10);
        }
    }

    private void assertRemainingTimeAtFinish(final int expectedTime) {
        FitHandler.Callback handler = new FitHandler.Callback() {
            @Override
            public void execute(long actualTime) {
                Assert.assertEquals(expectedTime, actualTime);
                done = true;
            }
        };
        fitHandler.onFinish(handler);
        while (!done) {
            clock.tick();
            Thread.yield();
        }
    }
}