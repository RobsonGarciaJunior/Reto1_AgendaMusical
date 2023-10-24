package com.example.agenda_musical_reto1.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val id: Int,
    val title: String,
    val artist: String,
    val album: String
): Parcelable


