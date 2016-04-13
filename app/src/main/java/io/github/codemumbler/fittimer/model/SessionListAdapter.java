package io.github.codemumbler.fittimer.model;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class SessionListAdapter extends ArrayAdapter {

    public SessionListAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
        List<Pose> poseQueue = new ArrayList<>();
        poseQueue.add(new Pose("Mountain Pose"));
        poseQueue.add(new Pose("Mountain Pose with arms up"));
        poseQueue.add(new Pose("Mountain Pose with arms up and hands clasped"));
        poseQueue.add(new Pose("Standing forward bend"));
        poseQueue.add(new Pose("Downward facing dog"));
        Session session = new Session(poseQueue);
        session.setName("Simple Yoga Session");
        super.add(session);
    }
}
