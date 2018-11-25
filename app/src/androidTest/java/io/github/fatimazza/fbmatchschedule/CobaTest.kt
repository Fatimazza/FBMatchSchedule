package io.github.fatimazza.fbmatchschedule

import android.content.ClipData.Item
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import io.github.fatimazza.fbmatchschedule.R.id.bottom_navigation
import io.github.fatimazza.fbmatchschedule.R.id.favorite_match
import io.github.fatimazza.fbmatchschedule.main.MainActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class CobaTest {

    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAddMatchesToFavorite() {

        Espresso.onView(withId(bottom_navigation))
                .check(matches(isDisplayed()))
        Espresso.onView(withId(favorite_match))
                .perform(click())

        Thread.sleep(2000)

        Espresso.onData(instanceOf(Item::class.java))
                .inAdapterView(
                        allOf(withId(R.id.favorite_item), isDisplayed()))
    }
}