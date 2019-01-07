package com.arctouch.upcomingmoviesapp.network


import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.arctouch.upcomingmoviesapp.movies.MoviesActivity
import org.junit.*
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.*

@RunWith(AndroidJUnit4::class) class RestApiTest {

    @Rule @JvmField var activityTestRule = ActivityTestRule(MoviesActivity::class.java)

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Test fun getFirstPageOfMovies() {
        TMdb.getLastMovies(SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time),1).observe(activityTestRule.activity, Observer {
            when (it?.status) {
                Resource.LOADING -> {
                    Log.d("getFirstPageOfMovies", "--> Loading...")

                }
                Resource.SUCCESS -> {
                    Log.d("getFirstPageOfMovies", "--> Success! | loaded ${it.data?.size ?: 0} records.")
                    Log.d("getFirstPageOfMovies", "--> Success! | ${it.data}")
                    Assert.assertEquals(20,it.data?.size ?: 0)
                }
                Resource.ERROR -> {
                    Log.d("getFirstPageOfMovies", "--> Error!")
                    Assert.fail()
                }
            }
        })

    }

    @Test fun getGenres() {
        TMdb.getGenres().observe(activityTestRule.activity, Observer {
            when (it?.status) {
                Resource.LOADING -> {
                    Log.d("getGenres", "--> Loading...")

                }
                Resource.SUCCESS -> {
                    Log.d("getGenres", "--> Success! | loaded ${it.data?.size ?: 0} records.")
                    Log.d("getGenres", "--> Success! | ${it.data}")
                    Assert.assertNotEquals(0,it.data?.size ?: 0)
                }
                Resource.ERROR -> {
                    Log.d("getGenres", "--> Error!")
                    Assert.fail()
                }
            }
        })

    }

}


