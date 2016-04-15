package io.github.codemumbler.fittimer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Session implements Parcelable {

    private ArrayList<Pose> poseQueue;
    private Pose currentPose;
    private String name;

    public Session(final List<Pose> poseQueue) {
        this.poseQueue = (ArrayList<Pose>) poseQueue;
    }

    protected Session(Parcel in) {
        name = in.readString();
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

    public void start() {
        next();
    }

    public Pose getCurrentPose() {
        return this.currentPose;
    }

    public boolean next() {
        if (!complete()) {
            this.currentPose = poseQueue.remove(0);
            return true;
        }
        return false;
    }


    public boolean complete() {
        return poseQueue.isEmpty();
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
}
