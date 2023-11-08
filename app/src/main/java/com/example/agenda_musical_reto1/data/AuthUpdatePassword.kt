package com.example.agenda_musical_reto1.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthUpdatePassword(
    val oldPassword: String,
    val newPassword: String
): Parcelable
