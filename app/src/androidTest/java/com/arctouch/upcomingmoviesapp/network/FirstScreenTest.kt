package com.arctouch.upcomingmoviesapp.network


import androidx.test.espresso.Espresso.onData
import androidx.test.platform.app.InstrumentationRegistry

import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest



import androidx.test.espresso.Espresso.onView

import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import com.arctouch.upcomingmoviesapp.R
import com.arctouch.upcomingmoviesapp.movies.MoviesActivity
import com.arctouch.upcomingmoviesapp.network.utils.WaitUtils
import org.hamcrest.CoreMatchers

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Mahmoud Abdurrahman (mahmoud.abdurrahman@crossover.com) on 2/12/18.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
public class MainActivityTests {

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<MoviesActivity> = ActivityTestRule(MoviesActivity::class.java)

    @Test
    fun testClickFirstItemOnList()
    {
        WaitUtils.waitTime()
        // Tap first item on Movie List
        // ----------------------
        onData(CoreMatchers.anything()).inAdapterView(CoreMatchers.allOf(withId(R.id.movies_list), isCompletelyDisplayed()))
            .atPosition(0).perform(click())


        WaitUtils.waitTime()
        // Verify Display of Overview Text
        // ----------------------
        val expectedText = InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.overview_title)
        onView(withId(R.id.overviewTitle)).check(matches(withText(expectedText)))
    }

}