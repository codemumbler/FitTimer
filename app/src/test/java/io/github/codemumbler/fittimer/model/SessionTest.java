package io.github.codemumbler.fittimer.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SessionTest {

    private static final String POSE_1 = "Pose 1";
    private static final String POSE_2 = "Pose 2";
    private static final String POSE_3 = "Pose 3";
    private static final String POSE_4 = "Pose 4";

    private List<Pose> poseQueue;
    private List<Pose> longerPoseQueue;
    private Session session;

    @Before
    public void setUp(){
        poseQueue = new ArrayList<>();
        poseQueue.add(new Pose(POSE_1));
        poseQueue.add(new Pose(POSE_2));
        longerPoseQueue = new ArrayList<>();
        longerPoseQueue.add(new Pose(POSE_1));
        longerPoseQueue.add(new Pose(POSE_2));
        longerPoseQueue.add(new Pose(POSE_3));
        longerPoseQueue.add(new Pose(POSE_4));
        session = new Session(poseQueue);
        session.next();
    }

    @Test
    public void firstPose() {
        Assert.assertEquals(POSE_1, session.getCurrentPose().getName());
    }

    @Test
    public void nextPastLast() {
        session.next();
        Assert.assertFalse(session.next());
    }

    @Test
    public void prev() {
        session.next();
        session.prev();
        Assert.assertEquals(POSE_1, session.getCurrentPose().getName());
    }

    @Test
    public void prevPastFirst() {
        Assert.assertFalse(session.prev());
    }

    @Test
    public void noTransitions() {
        Assert.assertFalse(session.hasTransitions());
    }

    @Test
    public void hasTransitions() {
        session = new Session(poseQueue, 10);
        Assert.assertTrue(session.hasTransitions());
    }

    @Test
    public void startIsNotInTransition() {
        startLongSession();
        Assert.assertFalse(session.isTransition());
    }

    @Test
    public void fromTransitionNextIsNotInTransition() {
        startLongSession();
        session.next();
        Assert.assertTrue(session.isTransition());
    }

    private void startLongSession() {
        session = new Session(longerPoseQueue, 10);
        session.next();
    }

    @Test
    public void nextThroughTransitionPose2() {
        startLongSession();
        session.next();
        session.next();
        Assert.assertEquals("Pose 2", session.getCurrentPose().getName());
    }

    @Test
    public void nextInTransitionPose2() {
        startLongSession();
        session.next();
        session.next();
        Assert.assertFalse(session.isTransition());
    }

    @Test
    public void nextToTransitionPose3() {
        startLongSession();
        session.next();
        session.next();
        session.next();
        Assert.assertEquals("Pose 3", session.getCurrentPose().getName());
    }

    @Test
    public void nextInTransitionPose3() {
        startLongSession();
        session.next();
        session.next();
        session.next();
        Assert.assertTrue(session.isTransition());
    }

    @Test
    public void prevBackToPose1() {
        startLongSession();
        session.next();
        session.next();
        session.prev();
        Assert.assertEquals("Pose 2", session.getCurrentPose().getName());
    }

    @Test
    public void prevBackToPose1NotInTransition() {
        startLongSession();
        session.next();
        session.next();
        session.prev();
        Assert.assertTrue(session.isTransition());
    }

    @Test
    public void prevToTransitionPose2() {
        startLongSession();
        session.next();
        session.next();
        session.next();
        session.prev();
        Assert.assertEquals("Pose 2", session.getCurrentPose().getName());
    }

    @Test
    public void prevToTransitionPose2True() {
        startLongSession();
        session.next();
        session.next();
        session.next();
        session.prev();
        Assert.assertFalse(session.isTransition());
    }
}
