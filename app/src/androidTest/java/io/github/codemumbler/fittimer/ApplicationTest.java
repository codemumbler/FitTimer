package io.github.codemumbler.fittimer;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class ApplicationTest {

    public static final int YOGA_WORKOUT_POSITION = 2;
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void displaysAssetWorkouts() {
        onData(anything()).atPosition(YOGA_WORKOUT_POSITION).check(matches(withText("Simple Yoga Session")));
    }

    @Test
    public void clickingStartsWorkout() {
        onData(anything()).atPosition(YOGA_WORKOUT_POSITION).perform(click());
        onView(withId(R.id.fullscreen_content)).check(matches(withText("Get ready…")));
    }
}