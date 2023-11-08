package com.example.agenda_musical_reto1.data.repository.remote

import com.example.agenda_musical_reto1.data.AuthLoginRequest
import com.example.agenda_musical_reto1.data.AuthUpdatePassword
import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.data.User
import com.example.agenda_musical_reto1.data.repository.IUserRepository
class UserRepository(private val repository : IUserRepository){
    suspend fun getUserLogin(authLoginRequest: AuthLoginRequest) = repository.getUserLogin(authLoginRequest)
    suspend fun registerUser(user: User)= repository.registerUser(user)
    suspend fun updateUserPassword(authUpdatePassword: AuthUpdatePassword) = repository.updateUserPassword(authUpdatePassword)
    suspend fun deleteUser() = repository.deleteUser()
    suspend fun getAllFavorites() = repository.getAllFavorites()
    suspend fun createFavorite(idSong: Int) = repository.createFavorite(idSong)
    suspend fun deleteFavorite(idSong: Int) = repository.deleteFavorite(idSong)
}