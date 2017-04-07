package io.github.codemumbler.fittimer.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Session implements Parcelable {

    private List<Pose> poseQueue;
    private int currentPose;
    private String name;
    private long transitionDuration;
    private boolean transition = true;

    public Session(final List<Pose> poseQueue) {
        this(poseQueue, -1);
    }

    public Session(final List<Pose> poseQueue, final long transitionDuration) {
        this.currentPose = -1;
        this.poseQueue = poseQueue;
        this.transitionDuration = transitionDuration;
    }

    protected Session(Parcel in) {
        name = in.readString();
        this.currentPose = -1;
        transitionDuration = in.readLong();
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
            if (hasTransitions()){
                if (!transition || currentPose == -1) {
                    this.currentPose++;
                }
                transition = !transition;
            } else {
                this.currentPose++;
            }
            return true;
        }
        return false;
    }

    public boolean complete() {
        if (currentPose + 1 >= poseQueue.size()) {
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasTransitions() {
        return (transitionDuration > 0);
    }

    public int getSessionSize() {
        return poseQueue.size();
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
        dest.writeLong(transitionDuration);
        dest.writeTypedList(poseQueue);
    }

    public boolean prev() {
        if (currentPose <= 0) {
            return false;
        }
        this.currentPose--;
        return true;
    }

    public JSONObject toJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("version", 1.0);
        jsonObject.put("name", name);
        jsonObject.put("transition-duration", transitionDuration);
        JSONArray array = new JSONArray();
        for (Pose pose : poseQueue) {
            array.put(pose.toJsonObject());
        }
        jsonObject.putOpt("poses", array);
        return jsonObject;
    }

    public long getTransitionDuration() {
        return transitionDuration;
    }

    public boolean isTransition() {
        return transition;
    }
}
