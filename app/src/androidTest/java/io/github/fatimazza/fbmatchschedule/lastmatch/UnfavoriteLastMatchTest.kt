package io.github.fatimazza.fbmatchschedule.lastmatch


import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import io.github.fatimazza.fbmatchschedule.R
import io.github.fatimazza.fbmatchschedule.R.id.*
import io.github.fatimazza.fbmatchschedule.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UnfavoriteLastMatchTest {

    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRemoveLastMatchFromFavorite() {

        Espresso.onView(withId(bottom_navigation))
                .check(matches(isDisplayed()))
        Espresso.onView(withId(last_match))
                .perform(click())

        Thread.sleep(4000)

        Espresso.onView(withId(last_match_list))
                .check(matches(isDisplayed()))
        Espresso.onView(withId(last_match_list))
                .perform(RecyclerViewActions
                        .scrollToPosition<RecyclerView.ViewHolder>(10))
        Espresso.onView(withId(last_match_list))
                .perform(RecyclerViewActions
                        .actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))

        Thread.sleep(4000)

        Espresso.onView(withId(ll_team_detail))
                .check(matches((isDisplayed())))

        Espresso.onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        Espresso.onView(withId(add_to_favorite))
                .perform(click())

        Espresso.onView(ViewMatchers.withText(R.string.favorite_removed))
                .check(matches(isDisplayed()))
        Espresso.pressBack()

        Espresso.onView(withId(bottom_navigation))
                .check(matches(isDisplayed()))
        Espresso.onView(withId(favorite_match))
                .perform(click())

        Thread.sleep(4000)

        Espresso.onView(withId(favorite_item))
                .check(doesNotExist())
    }
}