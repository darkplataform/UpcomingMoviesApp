# Upcoming Movies App powered by TMdb
### Summary 
Android app capable of show the next first 50 upcoming movies starting today, it shows the name, 
a poster image, the genre(s) associated, the release date and a detail with the overview

#### Architecture

The application was build with Kotlin uses MVP Architecture with coroutines
 needed for the background network operations. Also it uses LiveData without the use of Rx
 instead we make use of a Coroutine Adapter for Retrofit to get apply the observable pattern
 without adding complexity.
 
The application was build using AndroidX libraries using the following migration guide: [AndroidX Migrate](https://developer.android.com/jetpack/androidx/migrate#migrate) 
 
The movie list only the first 50 movies loading on demand using a customized paging due the restriction of elements
 to be displayed.
 
 ### Libraries/Dependencies
 * [Retrofit2](http://square.github.io/retrofit/) for retrieving remote data
 * [Retrofit2 converter-gson](https://github.com/square/retrofit/tree/master/retrofit-converters/gson)
 * [Retrofit2 Coroutine Adapter](https://github.com/JakeWharton/retrofit2-kotlin-coroutines-adapter) for
 easily use LiveData with Retrofit2
 * [Picasso](http://square.github.io/picasso/) for loading images efficiently into the views
 * [Mockito](https://github.com/mockito/mockito)  for mocking classes for unit testing



### Pre-requisites / How to run:

* N/A

### Limitations/Known issues
* The movie list list inside will only show the first 50 upcoming movies 



### Coroutines purpose in this project:

* The implementation of the Network layer was done with some examples and templates that allow me to use Retrofit2
 without Rx and using coroutines.
* NetworkCallDSL was a template that allow me to encapsulated the deferred call of Retrofit and convert
 it to a LiveData Observable.
* Executing sequential background/network operations: getGenres() -> getMovies() the last one needed the list
 of genres to display the name using the genre id.



 


