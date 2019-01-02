package com.arctouch.upcomingmoviesapp.data

data class Movie constructor(
    var id:String="",
    var title: String = "",
    var poster_path: String = "",
    var genre_ids: List<Int>,
    var genre_ids_text: ArrayList<String>,
    var release_date:String=""
)