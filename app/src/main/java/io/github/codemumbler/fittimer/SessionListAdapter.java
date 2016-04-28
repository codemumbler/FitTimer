package io.github.codemumbler.fittimer;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import io.github.codemumbler.fittimer.model.Pose;
import io.github.codemumbler.fittimer.model.Session;

public class SessionListAdapter extends ArrayAdapter {

    public SessionListAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
        List<Pose> poseQueue = new ArrayList<>();
        poseQueue.add(new Pose("Mountain Pose", 15000));
        poseQueue.add(new Pose("Mountain Pose with arms up", 20000));
        poseQueue.add(new Pose("Mountain Pose with arms up and hands clasped", 15000));
        poseQueue.add(new Pose("Standing forward bend", 20000));
        poseQueue.add(new Pose("Downward facing dog", 35000));
        Session session = new Session(poseQueue);
        session.setName("Simple Yoga Session");
        super.add(session);
    }
}
