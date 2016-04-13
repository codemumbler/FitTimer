package io.github.codemumbler.fittimer.model;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class SessionCreatorListAdapter extends ArrayAdapter {

    public SessionCreatorListAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
    }

    public List<Pose> getPoseQueue() {
        List<Pose> poseQueue = new ArrayList<>();
        for (int index = 0; index < getCount(); index++) {
            poseQueue.add((Pose) super.getItem(index));
        }
        return poseQueue;
    }
}
