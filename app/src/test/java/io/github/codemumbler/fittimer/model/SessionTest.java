package io.github.codemumbler.fittimer.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SessionTest {

    private static final String POSE_1 = "Pose 1";
    private static final String POSE_2 = "Pose 2";

    private PoseQueue poseQueue;
    private Session session;

    @Before
    public void setUp(){
        poseQueue = new PoseQueue();
        poseQueue.add(new Pose(POSE_1));
        poseQueue.add(new Pose(POSE_2));
        session = new Session(poseQueue);
    }

    @Test
    public void firstPose() {
        session.start();
        Assert.assertEquals(POSE_1, session.poseName());
    }

    @Test
    public void next() {
        session.start();
        session.next();
        Assert.assertEquals(POSE_2, session.poseName());
    }
}
