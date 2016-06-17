package io.github.codemumbler.fittimer;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class FitTimerApplication extends Application {
  private Tracker mTracker;

  /**
   * Gets the default {@link Tracker} for this {@link Application}.
   * @return tracker
   */
  synchronized public Tracker getDefaultTracker() {
    if (mTracker == null) {
      GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
      mTracker = analytics.newTracker(R.xml.app_tracker);
    }
    return mTracker;
  }
}
