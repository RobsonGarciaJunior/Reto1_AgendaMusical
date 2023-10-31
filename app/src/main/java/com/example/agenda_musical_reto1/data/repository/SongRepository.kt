package com.example.agenda_musical_reto1.data.repository

import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.utils.Resource

interface SongRepository {
    suspend fun getSongs() : Resource<List<Song>>
    suspend fun getSong(id: Int) : Resource<Song>
    suspend fun createSong(song: Song) : Resource<Int>
    suspend fun updateSong(id: Int, song: Song) : Resource<Int>
    suspend fun deleteSong(id: Int) : Resource<Int>
}