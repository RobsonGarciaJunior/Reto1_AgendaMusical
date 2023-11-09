package com.example.agenda_musical_reto1.utils

object ValidationUtils {
    fun isEmailValid(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }
    fun arePasswordsMatching(password1: String, password2: String): Boolean {
        return password1 == password2
    }
    fun areAllFieldsFilled(vararg fields: String): Boolean {
        return fields.all { it.isNotEmpty() }
    }
}