package com.example.agenda_musical_reto1.data.repository

import com.example.agenda_musical_reto1.data.AuthLoginRequest
import com.example.agenda_musical_reto1.data.AuthUpdatePassword
import com.example.agenda_musical_reto1.data.LoginResponse
import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.data.User
import com.example.agenda_musical_reto1.utils.Resource

interface IUserRepository {
    suspend fun getUserLogin(authLoginRequest: AuthLoginRequest): Resource<LoginResponse> //DEVOLVERÁ EL TOKEN, NO?
    suspend fun registerUser(user: User): Resource<Int>
    suspend fun updateUserPassword(authUpdatePassword: AuthUpdatePassword): Resource<Int>
    suspend fun deleteUser(): Resource<Int>
    suspend fun getAllFavorites(): Resource<List<Song>>
    suspend fun createFavorite(idSong: Int): Resource<Int>
    suspend fun deleteFavorite(idSong: Int): Resource<Int>


}