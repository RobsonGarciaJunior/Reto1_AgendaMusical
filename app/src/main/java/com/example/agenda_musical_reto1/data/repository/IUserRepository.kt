package com.example.agenda_musical_reto1.data.repository

import com.example.agenda_musical_reto1.data.User
import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.utils.Resource

interface IUserRepository {
    suspend fun getUserLogin(id: Int): Resource<User>
    suspend fun registerUser(user: User): Resource<Int>
    suspend fun updateUserPassword(id: Int, user: User): Resource<Int>
    suspend fun deleteUser(id: Int): Resource<Int>
    suspend fun getAllFavorites(id: Int): Resource<List<Song>>
    suspend fun createFavorite(song: Song): Resource<Int>
    suspend fun deleteFavorite(idUser: Int, idFavorite: Int): Resource<Int>


}