package com.example.agenda_musical_reto1.data.repository.remote

import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.data.repository.ISongRepository

class SongRepository(private val repository : ISongRepository) {
    suspend fun getSongs() = repository.getSongs()
    suspend fun getSong(id: Int) = repository.getSong(id)
    suspend fun createSong(song: Song) = repository.createSong(song)
    suspend fun updateSong(id: Int, song: Song) = repository.updateSong(id, song)
    suspend fun deleteSong(id: Int) = repository.deleteSong(id)
}