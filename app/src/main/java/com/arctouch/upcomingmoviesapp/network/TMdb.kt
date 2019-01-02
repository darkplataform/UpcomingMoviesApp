package com.arctouch.upcomingmoviesapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object TMdb {
    fun getLastMovies(releaseDate: String) = networkCall<LastMovResponse, List<Movies>> {
        client = TMdbAPI.tmdbService.getLastMovies(releaseDate)
    }
}

object TMdbAPI {
    var API_BASE_URL: String = "https://api.themoviedb.org/3/"
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
        @GET("discover/movie?api_key=1f54bd990f1cdfb230adb312546d765d&language=en-US&sort_by=release_date.asc&include_adult=false&include_video=false&page=1")
        fun getLastMovies(@Query("primary_release_date.gte") releaseDate: String): Deferred<Response<LastMovResponse>>
    }
}

data class LastMovResponse(val results: List<Movies>): BaseApiResponse<Movies>(), DataResponse<List<Movies>> {
    override fun retrieveData(): List<Movies> = results
}

abstract class BaseApiResponse<T> {
    var page: Int = 0
    var total_results: Int = 0
    var total_pages:  Int = 0

}

data class Movies(val id: Int, val name: String, val full_name: String, val description: String, val git_url:String)