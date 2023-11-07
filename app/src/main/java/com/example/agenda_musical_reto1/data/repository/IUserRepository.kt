package com.example.agenda_musical_reto1.data.repository

import com.example.agenda_musical_reto1.data.AuthLoginRequest
import com.example.agenda_musical_reto1.data.AuthUpdatePassword
import com.example.agenda_musical_reto1.data.User
import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.utils.Resource

interface IUserRepository {
    //TODO PREGUNTAR SI AQUI TMB HACE FALTA QUE CREEMOS DIFERENTES MODELOS PARA LAS DIFERENTES LLAMADAS DE LOS SERVICIOS O LE PASO LOS DATOS A PELO
    suspend fun getUserLogin(authLoginRequest: AuthLoginRequest): Resource<String> //DEVOLVERÁ EL TOKEN, NO?
    suspend fun registerUser(user: User): Resource<Int>
    suspend fun updateUserPassword(authUpdatePassword: AuthUpdatePassword): Resource<Int>
    suspend fun deleteUser(): Resource<Int>
    suspend fun getAllFavorites(): Resource<List<Song>>
    suspend fun createFavorite(idSong: Int): Resource<Int>
    suspend fun deleteFavorite(idSong: Int): Resource<Int>


}