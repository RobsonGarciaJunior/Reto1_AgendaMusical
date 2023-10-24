package com.example.agenda_musical_reto1.data.repository.remote

import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.data.User
import com.example.agenda_musical_reto1.data.repository.UserRepository

class RemoteUserDataSource: BaseDataSource(), UserRepository {
    override suspend fun getUserById(id: Integer) = getResult {
        RetrofitClient.apiInterface.getUser(id)
    }

    override suspend fun updateUser(id:Int, user: User ) = getResult{
        RetrofitClient.apiInterface.updateUser(id, user)
    }

    override suspend fun createUser(user: User) = getResult{
        RetrofitClient.apiInterface.createUser(user)
    }

    override suspend fun deleteUser(id: Int) = getResult{
        RetrofitClient.apiInterface.deleteUser(id)
    }

    override suspend fun getFavorites(id: Integer) = getResult {
        RetrofitClient.apiInterface.getFavorites(id)
    }

    override suspend fun addFavorite(song: Song) = getResult {
        RetrofitClient.apiInterface.addFavorite(song)
    }

    override suspend fun deleteFavorite(idUser: Int, idFavorite: Int) = getResult {
        RetrofitClient.apiInterface.deleteFavorite(idUser, idFavorite)
    }

}