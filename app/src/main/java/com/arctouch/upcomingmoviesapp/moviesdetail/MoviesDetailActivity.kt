package com.arctouch.upcomingmoviesapp.moviesdetail

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.arctouch.upcomingmoviesapp.R
import com.arctouch.upcomingmoviesapp.data.Movie
import kotlinx.android.synthetic.main.activity_movies_detail.*

class MoviesDetailActivity : AppCompatActivity() {

    lateinit var movieObj:Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_detail)
        //setSupportActionBar(toolbar)


        movieObj= intent.getParcelableExtra<Movie>(EXTRA_MOVIE_OBJ)

        with(findViewById<ImageView>(R.id.poster)) {
            com.squareup.picasso.Picasso.get().load("https://image.tmdb.org/t/p/w500/"+movieObj.poster_path)
                .error(com.arctouch.upcomingmoviesapp.R.drawable.ic_no_movies_in_24dp).into(this)
        }


        with(findViewById<ImageView>(R.id.backdrop)) {
            com.squareup.picasso.Picasso.get().load("https://image.tmdb.org/t/p/w500/"+movieObj.backdrop_path)
                .error(com.arctouch.upcomingmoviesapp.R.drawable.ic_no_movies_in_24dp).into(this)
        }

        with(findViewById<TextView>(R.id.title)) {
            text = movieObj.title
        }
        with(findViewById<TextView>(R.id.genres)) {
            if(movieObj.genre_ids_text!=null)
                text=movieObj.genre_ids_text?.joinToString().orEmpty()
        }
        with(findViewById<TextView>(R.id.releaseDate)) {
            text = "release: ${movieObj.release_date}"
        }
        with(findViewById<TextView>(R.id.overview)) {
            text =movieObj.overview
        }
    }

    companion object {
        const val EXTRA_MOVIE_OBJ = "MOVIE_OBJ"
    }
}
