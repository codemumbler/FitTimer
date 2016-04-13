package io.github.codemumbler.fittimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import io.github.codemumbler.fittimer.model.Pose;
import io.github.codemumbler.fittimer.model.Session;
import io.github.codemumbler.fittimer.model.SessionCreatorListAdapter;

public class CreateSession extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);

        ListView listView = getListView();
        listView.setTextFilterEnabled(true);
        listView.setAdapter(new SessionCreatorListAdapter(this));
        setListViewHeightBasedOnItems(getListView());
    }

    public void addPose(View view) {
        EditText text = (EditText) findViewById(R.id.poseName);
        ((SessionCreatorListAdapter) getListView().getAdapter()).add(new Pose(text.getText().toString()));
        setListViewHeightBasedOnItems(getListView());
    }

    public void createNewSession(View view) {
        List<Pose> queue = ((SessionCreatorListAdapter) getListView().getAdapter()).getPoseQueue();
        Session session = new Session(queue);
        session.setName("Custom Session");
        Intent data = new Intent();
        data.putExtra("newCustomSession", session);
        setResult(RESULT_OK, data);
        finish();
    }

    public ListView getListView() {
        return (ListView) findViewById(R.id.newSessionPose);
    }

    private void setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {
            int numberOfItems = listAdapter.getCount();
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }
            int totalDividersHeight = listView.getDividerHeight() * (numberOfItems - 1);
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();
        }
    }
}
