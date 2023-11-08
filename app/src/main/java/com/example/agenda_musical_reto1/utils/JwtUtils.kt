package com.example.agenda_musical_reto1.utils

import android.util.Base64
import com.example.agenda_musical_reto1.data.User
import org.json.JSONObject
import java.io.UnsupportedEncodingException


object JWTUtils {
    @Throws(Exception::class)
    fun decoded(token: String): User? {
        try {
            val tokenBody = token.split(".")[1]
            val decodedTokenString = getJson(tokenBody)
            val jsonObject = JSONObject(decodedTokenString)
            val userId = jsonObject.getInt("userId")
            val email = jsonObject.getString("sub")
            val name = jsonObject.getString("name")
            val surname = jsonObject.getString("surname")
            return User(userId, name, surname, email, null)
        } catch (e: UnsupportedEncodingException) {
            //Error
        }
        return null
    }

    @Throws(UnsupportedEncodingException::class)
    private fun getJson(strEncoded: String): String {
        return Base64.decode(strEncoded, Base64.URL_SAFE).decodeToString()
    }
}