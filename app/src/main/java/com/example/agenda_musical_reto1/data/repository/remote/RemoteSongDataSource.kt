package com.example.agenda_musical_reto1.data.repository.remote

import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.data.repository.ISongRepository

class RemoteSongDataSource: BaseDataSource(), ISongRepository {
    override suspend fun getSongs() = getResult {
        RetrofitClient.apiInterface.getSongs()
    }

    override suspend fun getSong(id: Int) = getResult {
        RetrofitClient.apiInterface.getSong(id)
    }

    override suspend fun createSong(song: Song) = getResult {
        RetrofitClient.apiInterface.createSong(song)
    }

    override suspend fun updateSong(id: Int, song: Song) = getResult {
        RetrofitClient.apiInterface.updateSong(id, song)
    }

    override suspend fun deleteSong(id: Int) = getResult {
        RetrofitClient.apiInterface.deleteSong(id)
    }

    override suspend fun getSongByAuthor(author: String) = getResult {
        RetrofitClient.apiInterface.getSongbyAuthor(author)
    }
}