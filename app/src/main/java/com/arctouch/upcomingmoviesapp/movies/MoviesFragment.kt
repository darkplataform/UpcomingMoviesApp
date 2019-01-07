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

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.appcompat.widget.PopupMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.arctouch.upcomingmoviesapp.BuildConfig
import com.arctouch.upcomingmoviesapp.R
import com.arctouch.upcomingmoviesapp.data.Movie
import com.arctouch.upcomingmoviesapp.moviesdetail.MoviesDetailActivity
import com.arctouch.upcomingmoviesapp.util.showSnackBar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*
import java.util.ArrayList

/**
 * Display a grid of [Movies]s. User can choose to view all or see detail.
 */
class MoviesFragment : Fragment(), MoviesContract.View {

    override lateinit var presenter: MoviesContract.Presenter


    private lateinit var noMoviesView: View
    private lateinit var noMoviesIcon: ImageView
    private lateinit var noMoviesMainView: TextView
    private lateinit var moviesView: LinearLayout


    /**
     * Listener for clicks on movies in the ListView.
     */
    internal var itemListener: MovieItemListener = object : MovieItemListener {
        override fun onMovieClick(clickedMovie: Movie) {
            presenter.openMovieDetails(clickedMovie)
        }

        override fun loadMoreMovies(page:Int){
            presenter.loadMovies(page)
        }

    }

    private val listAdapter = MoviesAdapter(ArrayList(0), itemListener)

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.movies_frag, container, false)

        // Set up tasks view
        with(root) {
            val listView = findViewById<ListView>(R.id.movies_list).apply { adapter = listAdapter }

            // Set up progress indicator
            findViewById<ScrollChildSwipeRefreshLayout>(R.id.refresh_layout).apply {
                setColorSchemeColors(
                        ContextCompat.getColor(activity!!, R.color.colorPrimary),
                        ContextCompat.getColor(activity!!, R.color.colorAccent),
                        ContextCompat.getColor(activity!!, R.color.colorPrimaryDark)
                )
                // Set the scrolling view in the custom SwipeRefreshLayout.
                scrollUpChild = listView
                setOnRefreshListener { presenter.loadMovies(1) }
            }


            moviesView = findViewById(R.id.moviesLL)

            // Set up  no tasks view
            noMoviesView = findViewById(R.id.noMovies)
            noMoviesIcon = findViewById(R.id.noMoviesIcon)
            noMoviesMainView = findViewById(R.id.noMoviesMain)

        }


        setHasOptionsMenu(true)

        return root
    }




    override fun setLoadingIndicator(active: Boolean) {
        val root = view ?: return
        with(root.findViewById<SwipeRefreshLayout>(R.id.refresh_layout)) {
            // Make sure setRefreshing() is called after the layout is done with everything else.
            post { isRefreshing = active }
        }
    }

    override fun showMovies(movies: List<Movie>,page:Int) {
        if(page>1){
            if(50>listAdapter.movies.size) {
                listAdapter.movies.addAll(movies.take(50 - listAdapter.movies.size))
                listAdapter.notifyDataSetChanged()
            }
        }else {
            listAdapter.movies = movies.toMutableList()
        }
        moviesView.visibility = View.VISIBLE
        noMoviesView.visibility = View.GONE
    }


    override fun showNoMovies() {
        showNoMoviesViews(resources.getString(R.string.no_movies_all), R.drawable.ic_no_movies_in_24dp, false)
    }

    private fun showNoMoviesViews(mainText: String, iconRes: Int, showAddView: Boolean) {
        moviesView.visibility = View.GONE
        noMoviesView.visibility = View.VISIBLE

        noMoviesMainView.text = mainText
        noMoviesIcon.setImageResource(iconRes)

    }


    override fun showMovieDetailsUi(movie: Movie) {
        // in it's own Activity, since it makes more sense that way and it gives us the flexibility
        // to show some Intent stubbing.
        val intent = Intent(context, MoviesDetailActivity::class.java).apply {
            putExtra(MoviesDetailActivity.EXTRA_MOVIE_OBJ, movie)
        }
        startActivity(intent)
    }



    override fun showLoadingMoviesError() {
        showMessage(getString(R.string.loading_movies_error))
    }

    private fun showMessage(message: String) {
        view?.showSnackBar(message, Snackbar.LENGTH_LONG)
    }

    private class MoviesAdapter(movies: MutableList<Movie>, private val itemListener: MovieItemListener)
        : BaseAdapter() {

        var movies: MutableList<Movie> = movies
            set(movies) {
                field = movies
                notifyDataSetChanged()
            }


        override fun getCount() = movies.size

        override fun getItem(i: Int) = movies[i]

        override fun getItemId(i: Int) = i.toLong()

        override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
            val movie = getItem(i)
            val rowView = view ?: LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.movie_item, viewGroup, false)

            with(rowView.findViewById<ImageView>(R.id.poster)) {
                Picasso.get().load(BuildConfig.tmdbUrlImages+movie.poster_path)
                    .error(R.drawable.ic_no_movies_in_24dp).into(this)
            }

            with(rowView.findViewById<TextView>(R.id.title)) {
                text = movie.title
            }
            with(rowView.findViewById<TextView>(R.id.genres)) {
                if(movie.genre_ids_text!=null)
                    text=movie.genre_ids_text?.joinToString().orEmpty()
            }
            with(rowView.findViewById<TextView>(R.id.releaseDate)) {
                text = "release: ${movie.release_date}"
            }


            rowView.setOnClickListener { itemListener.onMovieClick(movie) }
            if(count-5==i){
                itemListener.loadMoreMovies((count/20)+1)
            }
            return rowView
        }
    }

    interface MovieItemListener {

        fun onMovieClick(clickedMovie: Movie)

        fun loadMoreMovies(page:Int)

    }

    companion object {

        fun newInstance() = MoviesFragment()
    }

}
