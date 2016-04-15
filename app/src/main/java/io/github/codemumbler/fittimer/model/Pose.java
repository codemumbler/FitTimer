package io.github.codemumbler.fittimer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pose implements Parcelable {

    private String name;
    private int duration;

    public Pose(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public Pose(String name) {
        this(name, 45);
    }

    protected Pose(Parcel in) {
        this.name = in.readString();
        this.duration = in.readInt();
    }

    public static final Creator<Pose> CREATOR = new Creator<Pose>() {
        @Override
        public Pose createFromParcel(Parcel in) {
            return new Pose(in);
        }

        @Override
        public Pose[] newArray(int size) {
            return new Pose[size];
        }
    };

    public String getName() {
        return name;
    }

    public int getDuration() { return duration; }

    @Override
    public String toString() {
        return name + " for " + duration + " seconds";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(duration);
    }
}
