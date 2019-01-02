/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arctouch.upcomingmoviesapp.movies

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.arctouch.upcomingmoviesapp.data.Movie
import com.arctouch.upcomingmoviesapp.network.Genre
import com.arctouch.upcomingmoviesapp.network.Resource
import com.arctouch.upcomingmoviesapp.network.TMdb
import com.arctouch.upcomingmoviesapp.network.TMdbAPI
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.coroutines.CoroutineContext

/**
 * Listens to user actions from the UI ([TasksFragment]), retrieves the data and updates the
 * UI as required.
 */
class MoviesPresenter(private val moviesView: MoviesContract.View,
                      private val moviesActivity: MoviesActivity,
                      private val uiContext: CoroutineContext = Main)
    : MoviesContract.Presenter {

    //override var currentFiltering = TasksFilterType.ALL_TASKS

    private var firstLoad = true
    private lateinit var listGenres:List<Genre>

    init {
        moviesView.presenter = this
    }

    override fun start() {
        MainScope().launch {
            val genresLoad=async {
                listGenres= TMdbAPI.tmdbService.getGenres().await().body()?.retrieveData().orEmpty()
            }.await()
            loadMovies(true,1)
        }
    }


    override fun loadMovies(page:Int) {
        // Simplification for sample: a network reload will be forced on first load.
        loadMovies( true, page)
        firstLoad = false
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the [TasksDataSource]
     * *
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private fun loadMovies(showLoadingUI: Boolean, page:Int) {


        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        //EspressoIdlingResource.increment() // App is busy until further notice

        val result = TMdb.getLastMovies(SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time),page).observe(moviesActivity, Observer {
            when (it?.status) {
                Resource.LOADING -> {
                    Log.d("GetLastMovies", "--> Loading...")
                    if (showLoadingUI) {
                        moviesView.setLoadingIndicator(true)
                    }
                }
                Resource.SUCCESS -> {
                    Log.d("GetLastMovies", "--> Success! | loaded ${it.data?.size ?: 0} records.")
                    Log.d("GetLastMovies", "--> Success! | ${it.data}")
                    if (showLoadingUI) {
                        moviesView.setLoadingIndicator(false)
                    }
                    processMovies(it.data, page)
                }
                Resource.ERROR -> {
                    Log.d("GetLastMovies", "--> Error!")
                    moviesView.showLoadingMoviesError()
                }
            }
        })
    }

    private fun processMovies(movies: List<Movie>?, page:Int) {
        if (movies ==null || movies.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
            processEmptyTasks()
        } else {
            // Show the list of tasks
            for (movie in movies){
                for(id in movie.genre_ids){
                    if (movie.genre_ids_text==null) movie.genre_ids_text= arrayListOf()
                    var genre=listGenres.find { genre -> genre.id==id }
                    if(genre!=null)
                        movie.genre_ids_text.add(genre.name)
                }
            }
            moviesView.showMovies(movies, page)
            // Set the filter label's text.

        }
    }


    private fun processEmptyTasks() {
        moviesView.showNoMovies()
    }



    override fun openMovieDetails(requestedMovie: Movie) {
        moviesView.showMovieDetailsUi(requestedMovie.id)
    }


}
