package com.example.agenda_musical_reto1.data.repository.remote

import com.example.agenda_musical_reto1.data.AuthLoginRequest
import com.example.agenda_musical_reto1.data.AuthUpdatePassword
import com.example.agenda_musical_reto1.data.LoginResponse
import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.data.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface APIInterface {

    ////////////////////////////////////////USERS////////////////////////////////////////
    @POST("auth/login")
    suspend fun getUserLogin(@Body authLoginRequest: AuthLoginRequest): Response<LoginResponse>

    @POST("auth/signup")
    suspend fun registerUser(@Body user: User): Response<Int>

    @PUT("users/me")
    suspend fun updateUserPassword(@Body authUpdatePassword: AuthUpdatePassword): Response<Int>

    @DELETE("users/me")
    suspend fun deleteUser(): Response<Int>

    @GET("users/me/favorites")
    suspend fun getAllFavorites(): Response<List<Song>>

    @POST("users/favorites")
    suspend fun createFavorite(@Body idSong: Int): Response<Int>

    @DELETE("users/me/favorites/{idSong}")
    suspend fun deleteFavorite(@Path("idSong") idFavorite: Int): Response<Int>

    ////////////////////////////////////////SONGS////////////////////////////////////////
    @GET("songs")
    suspend fun getSongs(): Response<List<Song>>

    @GET("songs/{id}")
    suspend fun getSong(@Path("id") id: Int): Response<Song>

    @POST("songs")
    suspend fun createSong(@Body song: Song): Response<Int>

    @PUT("songs/{id}")
    suspend fun updateSong(@Path("id") id: Int, @Body song: Song): Response<Int>

    @DELETE("songs/{id}")
    suspend fun deleteSong(@Path("id") id: Int): Response<Int>
}