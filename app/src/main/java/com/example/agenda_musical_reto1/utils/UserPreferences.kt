package com.example.agenda_musical_reto1.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.agenda_musical_reto1.R
import com.example.agenda_musical_reto1.data.User

class UserPreferences {

    private val sharedPreferences: SharedPreferences by lazy {
        MyApp.context.getSharedPreferences(
            MyApp.context.getString(R.string.app_name), Context.MODE_PRIVATE
        )
    }

    companion object {
        const val USER_TOKEN = "user_token"
        var loggedUser: User? = null
    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Function to fetch auth token
     */
    fun fetchAuthToken(): String? {
        return sharedPreferences.getString(USER_TOKEN, null)
    }

    fun getLoggedUser(): User? {
        return loggedUser
    }

    fun saveLoggedUser(user: User) {
        val editor = sharedPreferences.edit()
        loggedUser = User(user.id, user.name, user.surname, user.email, null)
        editor.apply()
    }
    fun unLogUser() {
        val editor = sharedPreferences.edit()
        loggedUser = null
        editor.apply()
    }
}