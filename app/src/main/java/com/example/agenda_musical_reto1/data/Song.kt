package com.example.agenda_musical_reto1.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val idSong: Int?,
    val title: String,
    val author: String,
    val url: String,
    var isFavorite: Boolean = false
): Parcelable {
    constructor(
        title: String,
        author: String,
        url: String) : this(null, title, author, url){
    }
}


