package io.github.codemumbler.fittimer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pose implements Parcelable {

    private String name;

    public Pose(String name) {
        this.name = name;
    }

    protected Pose(Parcel in) {
        name = in.readString();
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
    }
}
