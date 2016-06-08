package io.github.codemumbler.fittimer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import io.github.codemumbler.fittimer.model.Session;

public class MainActivity extends AppCompatActivity {

    public static final int CREATE_CUSTOM_SESSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listView = getListView();
        listView.setTextFilterEnabled(true);
        listView.setAdapter(new SessionListAdapter(this));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                playSession(view, (Session) parent.getItemAtPosition(position));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void playSession(View view, Session itemAtPosition) {
        Intent intent = new Intent(this, PlaySessionActivity.class);
        intent.putExtra("currentSession", itemAtPosition);
        startActivity(intent);
    }

    public void createSession(View view) {
        Intent intent = new Intent(this, CreateSession.class);
        startActivityForResult(intent, CREATE_CUSTOM_SESSION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_CUSTOM_SESSION) {
            if (resultCode == RESULT_OK) {
                Session session = data.getExtras().getParcelable("newCustomSession");
                ((SessionListAdapter)getListView().getAdapter()).add(session);
            }
        }
    }

    public ListView getListView() {
        return (ListView) findViewById(R.id.workoutListView);
    }
}
