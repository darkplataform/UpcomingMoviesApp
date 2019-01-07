package com.arctouch.upcomingmoviesapp.network

import com.arctouch.upcomingmoviesapp.BuildConfig
import com.arctouch.upcomingmoviesapp.data.Movie
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object TMdb {
    fun getLastMovies(releaseDate: String, page: Int) = networkCall<LastMovResponse, List<Movie>> {
        client = TMdbAPI.tmdbService.getLastMovies(releaseDate,page)
    }

    fun getGenres() = networkCall<GenresResponse, List<Genre>> {
        client = TMdbAPI.tmdbService.getGenres()
    }
}

object TMdbAPI {
    var API_BASE_URL: String = BuildConfig.tmdbUrl
    var httpClient = OkHttpClient.Builder()
    var builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
    var retrofit = builder
        .client(httpClient.build())
        .build()

    var tmdbService = retrofit.create<TMdbService>(TMdbService::class.java)

    interface TMdbService {
        @GET("discover/movie?language=en-US&sort_by=primary_release_date.asc&include_adult=false&include_video=false")
        fun getLastMovies(
                @Query("primary_release_date.gte") releaseDate: String,
                @Query("page") page: Int,
                @Query("api_key") apiKey:String=BuildConfig.tmdbApiKey
        ): Deferred<Response<LastMovResponse>>

        @GET("genre/movie/list?language=en-US")
        fun getGenres(@Query("api_key") apiKey:String=BuildConfig.tmdbApiKey): Deferred<Response<GenresResponse>>
    }
}

data class Genre(val id:Int, val name:String)

data class GenresResponse(val genres: List<Genre>): DataResponse<List<Genre>> {
    override fun retrieveData(): List<Genre> = genres
}

data class LastMovResponse(val results: List<Movie>): BaseApiResponse<Movie>(), DataResponse<List<Movie>> {
    override fun retrieveData(): List<Movie> = results
}

abstract class BaseApiResponse<T> {
    var page: Int = 0
    var total_results: Int = 0
    var total_pages:  Int = 0

}

