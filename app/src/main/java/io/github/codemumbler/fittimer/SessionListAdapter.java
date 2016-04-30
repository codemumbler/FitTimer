package io.github.codemumbler.fittimer;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.github.codemumbler.fittimer.model.Pose;
import io.github.codemumbler.fittimer.model.Session;

public class SessionListAdapter extends ArrayAdapter {

    public SessionListAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
        try {
            for (String filename : getContext().getAssets().list("")) {
                if (filename.endsWith(".json")) {
                    addSessionFromFile(filename);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addSessionFromFile(String filename) {
        StringBuilder jsonData = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getContext().getAssets().open(filename), "UTF-8"));

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                jsonData.append(mLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        JSONObject obj = null;
        try {
            obj = new JSONObject(jsonData.toString());
            List<Pose> poseQueue = new ArrayList<>();
            JSONArray array = obj.getJSONArray("poses");
            for (int index = 0; index < array.length(); index++) {
                JSONObject poseObj = array.getJSONObject(index);
                Pose pose = new Pose(poseObj.getString("name"), poseObj.getLong("duration"));
                poseQueue.add(pose);
            }
            Session session = new Session(poseQueue);

            session.setName(obj.getString("name"));
            super.add(session);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
