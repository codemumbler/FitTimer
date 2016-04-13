package io.github.codemumbler.fittimer.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PoseQueueTest {

    private static final String POSE_NAME = "Pose 1";
    private List<Pose> poseQueue;

    @Before
    public void setUp() {
        poseQueue = new ArrayList<>();
    }

    @Test
    public void addToQueue() {
        poseQueue.add(new Pose(POSE_NAME));
        Assert.assertEquals(1, poseQueue.size());
    }

    @Test
    public void removeFirstItem() {
        poseQueue.add(new Pose(POSE_NAME));
        poseQueue.remove(0);
        Assert.assertEquals(0, poseQueue.size());
    }

    @Test
    public void removeReturnInfo() {
        poseQueue.add(new Pose(POSE_NAME));
        Assert.assertEquals(POSE_NAME, poseQueue.remove(0).getName());
    }
}
