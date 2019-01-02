package com.arctouch.upcomingmoviesapp.movies

import com.arctouch.upcomingmoviesapp.BasePresenter
import com.arctouch.upcomingmoviesapp.BaseView
import com.arctouch.upcomingmoviesapp.data.Movie

interface MoviesContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showMovies(movies: List<Movie>, page:Int)


        fun showMovieDetailsUi(movieId: String)


        fun showLoadingMoviesError()

        fun showNoMovies()

//        fun showActiveFilterLabel()
//
//        fun showCompletedFilterLabel()
//
//        fun showAllFilterLabel()
//
//        fun showFilteringPopUpMenu()
    }

    interface Presenter : BasePresenter {

        //var currentFiltering: TasksFilterType


        fun loadMovies(page: Int)


        fun openMovieDetails(requestedMovie: Movie)

    }
}