package com.example.agenda_musical_reto1.data.repository

import com.example.agenda_musical_reto1.data.User
import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.utils.Resource

interface UserRepository {
    suspend fun getUserById(id: Int) : Resource<User>
    suspend fun updateUser(id: Int, user: User) : Resource<Int>
    suspend fun createUser(user: User) : Resource<Int>
    suspend fun deleteUser(id: Int) : Resource<Int>
    suspend fun getFavorites(id: Integer) : Resource<List<Song>>
    suspend fun addFavorite(song: Song) : Resource<Integer>
    suspend fun deleteFavorite(idUser: Int, idFavorite: Int) : Resource<Integer>


}