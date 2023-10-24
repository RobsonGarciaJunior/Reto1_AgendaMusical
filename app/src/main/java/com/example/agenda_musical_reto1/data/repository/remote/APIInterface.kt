package com.example.agenda_musical_reto1.data.repository.remote

import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.data.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Path

interface APIInterface {

    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Integer): Response<List<User>>

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body user: User) : Response<Integer>

    @POST("users")
    suspend fun createUser(@Body user: User) : Response<Integer>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Int) : Response<Void>

    @GET("users/{id}/favorites")
    suspend fun getFavorites(@Path("id") id: Integer): Response<List<Song>>

    @POST("users/favorites")
    suspend fun addFavorite(@Body song: Song) : Response<Integer>

    @DELETE("users/{idUser}/favorites/{idFavorite}")
    suspend fun deleteFavorite(@Path("idUser") idUser: Int, @Path("idFavorite") idFavorite: Int) : Response<Integer>

    @GET("songs")
    suspend fun getSongs(): Response<List<Song>>

    @GET("songs/{id}")
    suspend fun getSong(@Path("id") id: Int): Response<Song>

    @POST("songs")
    suspend fun createSong(@Body song: Song) : Response<Int>

    @PUT("songs/{id}")
    suspend fun updateSong(@Path("id") id: Int, @Body song: Song) : Response<Integer>

    @DELETE("songs/{id}")
    suspend fun deleteSong(@Path("id") id: Int) : Response<Integer>
}