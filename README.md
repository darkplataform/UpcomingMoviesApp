# Upcoming Movies App powered by TMdb

### Features 
######List the first 50 upcoming movies including name, poster image, genre(s), overview and a release date

###Highlights
 
###### Program Language: Kotlin
###### Architecture: MVP
###### Network: Retrofit
###### Threading and Concurrence with Coroutines
###### Refactor with latest AndroidX libraries

Status: Build without errors


### Special Build Instructions:

* N/A


### List of Third Party Libraries:

* retrofit2: was selected for its simplicity and also because can work with Coroutines, I stablish a TMdb retrofit class with the operations defined. 
* retrofit2 coroutines-adapter: By jakewharton, thanks to the brilliant Jake we all can use this adapter to easily use LiveData with Retrofit2.  
* Picasso2: makes the image handling easy, only define the URL of the image and the rest is handled by Picasso. 

### How was the use of coroutines?:

* My first time, very interesting.
* The implementation of the Network layer was done with some examples and templates that allow me to use Retrofit2 without Rx and using coroutines.
* NetworkCallDSL was a template that allow me to encapsulated the deferred call of Retrofit and convert it to a LiveData Observable.
* I also applied coroutines for executing sequential background/network operations: getGenres() -> getMovies() the last one needed the list of genres to display the name instead of just the code.



### How was the refactoring of MVP to use AndroidX Libraries:

Was a nightmare at first, there are a couple of big changes in the Test Libraries but after that you just needed to apply the migration guide: https://developer.android.com/jetpack/androidx/migrate#migrate 


