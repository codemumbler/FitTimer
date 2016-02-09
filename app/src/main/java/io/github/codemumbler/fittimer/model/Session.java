package io.github.codemumbler.fittimer.model;

public class Session {

    private final PoseQueue poseQueue;
    private Pose currentPose;

    public Session(final PoseQueue poseQueue) {
        this.poseQueue = poseQueue;
    }

    public void start() {
        next();
    }

    public String poseName() {
        return this.currentPose.getName();
    }

    public boolean next() {
        if (!complete()) {
            this.currentPose = poseQueue.removeFirst();
            return true;
        }
        return false;
    }


    public boolean complete() {
        return poseQueue.isEmpty();
    }
}
