package com.example.agenda_musical_reto1.ui.viewmodels.users

import androidx.lifecycle.LiveData
import com.example.agenda_musical_reto1.data.AuthLoginRequest
import com.example.agenda_musical_reto1.data.AuthUpdatePassword
import com.example.agenda_musical_reto1.data.LoginResponse
import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.data.User
import com.example.agenda_musical_reto1.utils.Resource

interface UserViewModelInterface {
    val user: LiveData<Resource<LoginResponse>?>
    val created: LiveData<Resource<Int>?>
    val updated: LiveData<Resource<Int>?>
    val deleted: LiveData<Resource<Int>?>
    val favoriteSongs: LiveData<Resource<List<Song>>>

    fun onUserLogin(email: String, password: String)
    suspend fun getUserLogin(authLoginRequest: AuthLoginRequest) : Resource<LoginResponse>
    fun onUserRegister(name: String, surname: String, email: String, password: String)
    suspend fun registerUser(user: User): Resource<Int>
    fun onUserUpdate(oldPassword: String, newPassword: String)
    suspend fun updateUserPassword(authUpdatePassword: AuthUpdatePassword): Resource<Int>
    fun onDeleteUser()
    suspend fun deleteUser(): Resource<Int>
    suspend fun obtainFavoriteSongs(): Resource<List<Song>>

}