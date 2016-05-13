package io.github.codemumbler.fittimer;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.codemumbler.fittimer.model.Pose;

public class SessionCreatorListAdapter extends BaseAdapter {

    private List<Pose> poseQueue = new ArrayList<>();
    private final Context context;
    private final Activity activity;

    public SessionCreatorListAdapter(final Context context, final Activity activity) {
        this.context = context;
        poseQueue.add(new Pose(""));
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return poseQueue.size();
    }

    @Override
    public Object getItem(int position) {
        return poseQueue.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.new_pose_item, null);

        }

        ImageButton durationButton = (ImageButton) convertView.findViewById(R.id.setDuration);
        durationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DurationDialogFragment durationDialogFragment = new DurationDialogFragment();
                durationDialogFragment.show(activity.getFragmentManager(), "DurationDialogFragment");

                durationDialogFragment.setListener(new DurationDialogFragment.DurationDialogListener() {
                    @Override
                    public void onDialogPositiveClick(DialogFragment dialog) {
                        EditText minutesEditText = (EditText) dialog.getDialog().findViewById(R.id.poseDurationMinutes);
                        EditText secondsEditText = (EditText) dialog.getDialog().findViewById(R.id.poseDurationSeconds);
                        Integer minutesInt = 0;
                        if (!minutesEditText.getText().toString().isEmpty()) {
                            minutesInt = Integer.valueOf(minutesEditText.getText().toString());
                        }
                        Integer secondsInt = 0;
                        if (!secondsEditText.getText().toString().isEmpty()) {
                            secondsInt = Integer.valueOf(secondsEditText.getText().toString());
                        }
                        long duration = ((minutesInt * 60) + secondsInt);
                        ((Pose) getItem(position)).setDuration(duration * 1000);
                    }
                });
            }
        });

        EditText poseNameTextView = (EditText) convertView.findViewById(R.id.newPoseName);
        poseNameTextView.setText(poseQueue.get(position).getName());
        poseNameTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                poseQueue.get(position).setName(s.toString());
            }
        });
        return convertView;
    }

    public List<Pose> getPoseQueue() {
        return poseQueue;
    }

    public void addNewItem() {
        poseQueue.add(new Pose(""));
    }
}
