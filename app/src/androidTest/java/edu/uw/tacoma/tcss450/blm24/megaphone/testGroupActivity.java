package edu.uw.tacoma.tcss450.blm24.megaphone;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.uw.tacoma.tcss450.blm24.megaphone.groupChat.GroupActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.endsWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class testGroupActivity {

    @Rule
    public ActivityTestRule<GroupActivity> activityTestRule =
            new ActivityTestRule<>(GroupActivity.class);

    @Test
    public void testSendText() {
        String number = "2534454500";
        onView(withText("OK")).perform(click());
        onView(withId(R.id.share_menu_item)).perform(click());
        onView(withText(R.string.share_content_alert_message))
                .inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withClassName(endsWith("EditText"))).perform(click());
        onView(withClassName(endsWith("EditText"))).perform(typeText(number));
        onView(withText("SEND")).perform(click());
    }

    @Test
    public void testSendTextInvalidNumber() {
        String number = "253";
        onView(withText("OK")).perform(click());
        onView(withId(R.id.share_menu_item)).perform(click());
        onView(withText(R.string.share_content_alert_message))
                .inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withClassName(endsWith("EditText"))).perform(click());
        onView(withClassName(endsWith("EditText"))).perform(typeText(number));
        onView(withText("SEND")).perform(click());
        onView(withText("Enter a valid phone number."))
                .inRoot(withDecorView(not(is(
                        activityTestRule.getActivity()
                                .getWindow().getDecorView()
                )))).check(matches(isDisplayed()));
    }

    @Test
    public void testChangeUsernameFail(){
        String user = "HuskyHippoIsChunkyAndHuskyAndBig";
        onView(withText("OK")).perform(click());
        onView(withId(R.id.action_change_username)).perform(click());
        onView(withClassName(endsWith("EditText"))).perform(click());
        onView(withClassName(endsWith("EditText"))).perform(typeText(user));
        onView(withText("UPDATE")).perform(click());
        onView(withText("Username too long"))
                .inRoot(withDecorView(not(is(
                        activityTestRule.getActivity()
                                .getWindow().getDecorView()
                )))).check(matches(isDisplayed()));
    }

    @Test
    public void testChangeUsername() {
        String user = "HuskyHippo";
        onView(withText("OK")).perform(click());
        onView(withId(R.id.action_change_username)).perform(click());
        onView(withClassName(endsWith("EditText"))).perform(click());
        onView(withClassName(endsWith("EditText"))).perform(typeText(user));
        onView(withText("UPDATE")).perform(click());
        onView(withText("Username set to "+ user))
                .inRoot(withDecorView(not(is(
                        activityTestRule.getActivity()
                                .getWindow().getDecorView()
                )))).check(matches(isDisplayed()));
    }



}
