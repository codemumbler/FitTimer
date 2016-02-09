package io.github.codemumbler.fittimer.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PoseQueueTest {

    private static final String POSE_NAME = "Pose 1";
    private PoseQueue poseQueue;

    @Before
    public void setUp() {
        poseQueue = new PoseQueue();
    }

    @Test
    public void addToQueue() {
        poseQueue.add(new Pose(POSE_NAME));
        Assert.assertEquals(1, poseQueue.size());
    }

    @Test
    public void removeFirstItem() {
        poseQueue.add(new Pose(POSE_NAME));
        poseQueue.removeFirst();
        Assert.assertEquals(0, poseQueue.size());
    }

    @Test
    public void removeReturnInfo() {
        poseQueue.add(new Pose(POSE_NAME));
        Assert.assertEquals(POSE_NAME, poseQueue.removeFirst().getName());
    }
}
