package io.github.codemumbler.fittimer.model;

import java.util.ArrayList;
import java.util.List;

import io.github.codemumbler.fittimer.model.Pose;

public class PoseQueue {
    private List<Pose> poses = new ArrayList<>();

    public void add(Pose pose) {
        poses.add(pose);
    }

    public int size() {
        return poses.size();
    }

    public Pose removeFirst() {
        return poses.remove(0);
    }

    public void add(String poseName) {
        add(new Pose(poseName));
    }

    public boolean isEmpty() {
        return poses.isEmpty();
    }
}
