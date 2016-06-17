package io.github.codemumbler.fittimer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import io.github.codemumbler.fittimer.model.Session;

public class PlaySessionActivity extends AppCompatActivity {

    private SessionRunner sessionRunner;
    private Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play_session);

        TextView contentView = (TextView) findViewById(R.id.fullscreen_content);

        Session session = getIntent().getExtras().getParcelable("currentSession");

        final TextView timerTextView = (TextView) findViewById(R.id.fullscreen_timer);
        sessionRunner = new SessionRunner(session);
        sessionRunner.setContentDisplay(contentView);
        sessionRunner.setTimerDisplay(timerTextView);

        sessionRunner.onReady(new SessionRunner.Callback() {
            @Override
            public void execute() {
                sessionRunner.start();
            }
        });
        sessionRunner.onComplete(new SessionRunner.Callback() {

            @Override
            public void execute() {
                finish();
            }
        });
        sessionRunner.init(getApplicationContext());

        FitTimerApplication application = (FitTimerApplication) getApplication();
        tracker = application.getDefaultTracker();
        tracker.setScreenName(PlaySessionActivity.class.getName());
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void pausePlay(View view) {
        if (!sessionRunner.isPaused()) {
            findViewById(R.id.play).setVisibility(View.VISIBLE);
            findViewById(R.id.pause).setVisibility(View.GONE);
        } else {
            findViewById(R.id.play).setVisibility(View.GONE);
            findViewById(R.id.pause).setVisibility(View.VISIBLE);
        }
        sessionRunner.pausePlay();
    }

    public void nextPose(View view) {
        sessionRunner.next();
    }

    public void prevPose(View view) {
        sessionRunner.prev();
    }

    @Override
    protected void onPause() {
        if (!sessionRunner.isPaused()) {
            sessionRunner.pausePlay();
            sessionRunner.complete();
        }
        super.onPause();
    }
}
