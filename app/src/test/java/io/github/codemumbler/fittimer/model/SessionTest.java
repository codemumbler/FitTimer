package io.github.codemumbler.fittimer.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SessionTest {

    private static final String POSE_1 = "Pose 1";
    private static final String POSE_2 = "Pose 2";

    private List<Pose> poseQueue;
    private Session session;

    @Before
    public void setUp(){
        poseQueue = new ArrayList<>();
        poseQueue.add(new Pose(POSE_1));
        poseQueue.add(new Pose(POSE_2));
        session = new Session(poseQueue);
    }

    @Test
    public void firstPose() {
        Assert.assertEquals(POSE_1, session.getCurrentPose().getName());
    }

    @Test
    public void next() {
        session.next();
        Assert.assertEquals(POSE_2, session.getCurrentPose().getName());
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
}
