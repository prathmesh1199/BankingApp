package com.example.bankingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private MainActivity mainActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MainActivity.class.getName() , null , false);

    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityTestRule.getActivity();
    }

    @Test
    public void TestLaunch() {
        View view = mainActivity.findViewById(R.id.login_email);
        assertNotNull(view);
    }

    @Test
    public void testLaunchOfLoginActivity() {
        assertNotNull(mainActivity.findViewById(R.id.login_button));

        onView(withId(R.id.login_email)).perform(typeText("a") , closeSoftKeyboard());
        onView(withId(R.id.login_pass)).perform(typeText("a") , closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(closeSoftKeyboard()).perform(click());

        Activity a = getInstrumentation().waitForMonitorWithTimeout(monitor , 5000);
        assertNotNull(a);
        a.finish();
    }


    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }
}