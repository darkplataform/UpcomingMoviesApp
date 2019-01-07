package com.arctouch.upcomingmoviesapp.data

import android.os.Parcel
import android.os.Parcelable

data class Movie (
    var id:String="",
    var title: String = "",
    var poster_path: String? = "",
    var genre_ids: List<Int>,
    var genre_ids_text: ArrayList<String>?,
    var release_date:String="",
    var backdrop_path:String?="",
    var overview:String=""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createIntArray().toList(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(poster_path)
        parcel.writeIntArray(genre_ids.toIntArray())
        parcel.writeStringList(genre_ids_text)
        parcel.writeString(release_date)
        parcel.writeString(backdrop_path)
        parcel.writeString(overview)

    }

    override fun describeContents(): Int {
        return 0
    }


    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}

