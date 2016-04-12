package io.github.codemumbler.fittimer.model;

public class Pose {

    private String name;

    public Pose(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
