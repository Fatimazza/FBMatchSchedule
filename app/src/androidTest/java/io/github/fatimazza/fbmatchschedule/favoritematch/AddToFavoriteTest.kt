package io.github.fatimazza.fbmatchschedule.favoritematch

import android.content.ClipData
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.R.id.*
import io.github.fatimazza.fbmatchschedule.main.MainActivity
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddToFavoriteTest {

    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAddMatchesToFavorite() {

        Espresso.onView(withId(bottom_navigation))
                .check(matches(isDisplayed()))
        Espresso.onView(withId(favorite_match))
                .perform(click())

        Thread.sleep(2000)

        Espresso.onView(withId(favorite_list))
                .check(matches(isDisplayed()))

        Espresso.onView(withId(favorite_item))
                .check(ViewAssertions.doesNotExist())

        Espresso.onView(withId(bottom_navigation))
                .check(matches(isDisplayed()))
        Espresso.onView(withId(last_match))
                .perform(click())

        Thread.sleep(2000)

        Espresso.onView(withId(last_match_list))
                .check(matches(isDisplayed()))
        Espresso.onView(withId(last_match_list))
                .perform(RecyclerViewActions
                        .scrollToPosition<RecyclerView.ViewHolder>(14))
        Espresso.onView(withId(last_match_list))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition<RecyclerView.ViewHolder>(14, click()))

        Thread.sleep(2000)

        Espresso.onView(withId(ll_team_detail))
                .check(matches((isDisplayed())))

        Espresso.onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        Espresso.onView(withId(add_to_favorite))
                .perform(click())

        Espresso.onView(ViewMatchers.withText(R.string.favorite_added))
                .check(matches(isDisplayed()))

        Espresso.pressBack()

        Espresso.onView(withId(bottom_navigation))
                .check(matches(isDisplayed()))
        Espresso.onView(withId(next_match))
                .perform(click())

        Thread.sleep(2000)

        Espresso.onView(withId(next_match_list))
                .check(matches(isDisplayed()))
        Espresso.onView(withId(next_match_list))
                .perform(RecyclerViewActions
                        .scrollToPosition<RecyclerView.ViewHolder>(14))
        Espresso.onView(withId(next_match_list))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition<RecyclerView.ViewHolder>(14, click()))

        Thread.sleep(2000)

        Espresso.onView(withId(ll_team_detail))
                .check(matches((isDisplayed())))

        Espresso.onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        Espresso.onView(withId(add_to_favorite))
                .perform(click())

        Espresso.onView(ViewMatchers.withText(R.string.favorite_added))
                .check(matches(isDisplayed()))

        Espresso.pressBack()

        Espresso.onView(withId(bottom_navigation))
                .check(matches(isDisplayed()))
        Espresso.onView(withId(favorite_match))
                .perform(click())

        Thread.sleep(2000)

        Espresso.onView(withId(favorite_list))
                .check(matches(isDisplayed()))

        Espresso.onData(
                CoreMatchers.instanceOf(ClipData.Item::class.java))
                .inAdapterView(
                        CoreMatchers.allOf(withId(favorite_item), isDisplayed()))
    }
}