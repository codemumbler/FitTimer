package io.github.codemumbler.fittimer.model;

import android.content.Context;
import android.widget.ArrayAdapter;

public class SessionListAdapter extends ArrayAdapter {

    public SessionListAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
        PoseQueue poseQueue = new PoseQueue();
        poseQueue.add("Mountain Pose");
        poseQueue.add("Mountain Pose with arms up");
        poseQueue.add("Mountain Pose with arms up and hands clasped");
        poseQueue.add("Standing forward bend");
        poseQueue.add("Downward facing dog");
        Session session = new Session(poseQueue);
        session.setName("Simple Yoga Session");
        super.add(session);
    }
}
