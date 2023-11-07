package com.example.agenda_musical_reto1.data.repository.remote

import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.data.User
import com.example.agenda_musical_reto1.data.repository.IUserRepository

class RemoteUserDataSource : BaseDataSource(), IUserRepository {
    override suspend fun getUserLogin(id: Int) = getResult {
        RetrofitClient.apiInterface.getUserLogin(id)
    }

    override suspend fun registerUser(user: User) = getResult {
        RetrofitClient.apiInterface.registerUser(user)
    }
    override suspend fun updateUserPassword(id: Int, user: User) = getResult {
        RetrofitClient.apiInterface.updateUserPassword(id, user)
    }
    override suspend fun deleteUser(id: Int) = getResult {
        RetrofitClient.apiInterface.deleteUser(id)
    }
    override suspend fun getAllFavorites(id: Int) = getResult {
        RetrofitClient.apiInterface.getAllFavorites(id)
    }

    override suspend fun createFavorite(song: Song) = getResult {
        RetrofitClient.apiInterface.createFavorite(song)
    }

    override suspend fun deleteFavorite(idUser: Int, idFavorite: Int) = getResult {
        RetrofitClient.apiInterface.deleteFavorite(idUser, idFavorite)
    }

}