package com.example.bankingapp;

import android.app.Instrumentation;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class Credit_DebitTest {

    @Rule
    public ActivityTestRule<Credit_Debit> ActivityTestRule = new ActivityTestRule<>(Credit_Debit.class);
    private Credit_Debit activity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(Credit_Debit.class.getName() , null , false);

    @Before
    public void setUp() throws Exception {
        activity = ActivityTestRule.getActivity();
    }

    @Test
    public void TestLaunch() {
        View view = activity.findViewById(R.id.edt_value);
        assertNotNull(view);
    }

    @Test
    public void check_value() {
        onView(withId(R.id.edt_value)).perform(typeText(String.valueOf(10)) , closeSoftKeyboard());
    }


    @After
    public void tearDown() throws Exception {
        activity = null;
    }
}