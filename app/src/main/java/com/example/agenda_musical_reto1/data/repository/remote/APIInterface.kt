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
    
    ////////////////////////////////////////USERS////////////////////////////////////////
    @GET("users/{id}")
    suspend fun getUserLogin(@Path("id") id: Int): Response<User>
    @POST("users")
    suspend fun registerUser(@Body user: User) : Response<Int>

    @PUT("users/{id}")
    suspend fun updateUserPassword(@Path("id") id: Int, @Body user: User) : Response<Int>
    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Int) : Response<Int>

    @GET("users/{id}/favorites")
    suspend fun getAllFavorites(@Path("id") id: Int): Response<List<Song>>

    @POST("users/favorites")
    suspend fun createFavorite(@Body song: Song) : Response<Int>

    @DELETE("users/{idUser}/favorites/{idFavorite}")
    suspend fun deleteFavorite(@Path("idUser") idUser: Int, @Path("idFavorite") idFavorite: Int) : Response<Int>

    ////////////////////////////////////////SONGS////////////////////////////////////////
    @GET("songs")
    suspend fun getSongs(): Response<List<Song>>

    @GET("songs/{id}")
    suspend fun getSong(@Path("id") id: Int): Response<Song>

    @POST("songs")
    suspend fun createSong(@Body song: Song) : Response<Int>

    @PUT("songs/{id}")
    suspend fun updateSong(@Path("id") id: Int, @Body song: Song) : Response<Int>

    @DELETE("songs/{id}")
    suspend fun deleteSong(@Path("id") id: Int) : Response<Int>
}