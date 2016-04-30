package io.github.codemumbler.fittimer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Session implements Parcelable {

    private List<Pose> poseQueue;
    private int currentPose;
    private String name;

    public Session(final List<Pose> poseQueue) {
        this.currentPose = -1;
        this.poseQueue = poseQueue;
    }

    protected Session(Parcel in) {
        name = in.readString();
        this.currentPose = -1;
        this.poseQueue = new ArrayList<>();
        in.readTypedList(poseQueue, Pose.CREATOR);
    }

    public static final Creator<Session> CREATOR = new Creator<Session>() {
        @Override
        public Session createFromParcel(Parcel in) {
            return new Session(in);
        }

        @Override
        public Session[] newArray(int size) {
            return new Session[size];
        }
    };

    public Pose getCurrentPose() {
        return this.poseQueue.get(currentPose);
    }

    public boolean next() {
        if (!complete()) {
            this.currentPose++;
            return true;
        }
        return false;
    }

    public boolean complete() {
        if (currentPose + 1 >= poseQueue.size())
            return true;
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(poseQueue);
    }

    public boolean prev() {
        if (currentPose <= 0) {
            return false;
        }
        this.currentPose--;
        return true;
    }
}
