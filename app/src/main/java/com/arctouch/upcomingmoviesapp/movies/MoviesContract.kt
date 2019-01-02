package com.arctouch.upcomingmoviesapp.movies

import com.arctouch.upcomingmoviesapp.BasePresenter
import com.arctouch.upcomingmoviesapp.BaseView
import com.arctouch.upcomingmoviesapp.data.Movie

interface MoviesContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun showMovies(movies: List<Movie>, page:Int)

        fun showMovieDetailsUi(movie: Movie)

        fun showLoadingMoviesError()

        fun showNoMovies()

    }

    interface Presenter : BasePresenter {

        fun loadMovies(page: Int)

        fun openMovieDetails(requestedMovie: Movie)

    }
}