package com.example.agenda_musical_reto1.data.repository.remote

import com.example.agenda_musical_reto1.data.AuthLoginRequest
import com.example.agenda_musical_reto1.data.AuthUpdatePassword
import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.data.User
import com.example.agenda_musical_reto1.data.repository.IUserRepository
import com.example.agenda_musical_reto1.utils.Resource

class RemoteUserDataSource : BaseDataSource(), IUserRepository {
    override suspend fun getUserLogin(authLoginRequest: AuthLoginRequest) = getResult {
        RetrofitClient.apiInterface.getUserLogin(authLoginRequest)
    }

    override suspend fun registerUser(user: User) = getResult {
        RetrofitClient.apiInterface.registerUser(user)
    }

    override suspend fun updateUserPassword(authUpdatePassword: AuthUpdatePassword): Resource<Int> = getResult {
        RetrofitClient.apiInterface.updateUserPassword(authUpdatePassword)
    }

    override suspend fun deleteUser() = getResult {
        RetrofitClient.apiInterface.deleteUser()
    }

    override suspend fun getAllFavorites() = getResult {
        RetrofitClient.apiInterface.getAllFavorites()
    }

    override suspend fun createFavorite(idSong: Int) = getResult {
        RetrofitClient.apiInterface.createFavorite(idSong)
    }

    override suspend fun deleteFavorite(idSong: Int) = getResult {
        RetrofitClient.apiInterface.deleteFavorite(idSong)
    }

}