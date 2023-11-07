package com.example.agenda_musical_reto1.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthLoginRequest(
    val email: String,
    val password: String
):Parcelable {
}