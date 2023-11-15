package com.example.agenda_musical_reto1.ui.viewmodels.songs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.utils.Resource

interface SongViewModelInterface {

    val songs: LiveData<Resource<List<Song>>>
    val created: MutableLiveData<Resource<Int>?>
    val updated: MutableLiveData<Resource<Int>?>
    val deleted: MutableLiveData<Resource<Int>?>
    val filteredSongs: LiveData<Resource<List<Song>>>
    val favoriteSongs: LiveData<Resource<List<Song>>>
    val createdFavorite: LiveData<Resource<Int>?>
    val deletedFavorite: LiveData<Resource<Int>?>
    val playlistsongs: LiveData<Resource<List<Song>>>

    fun updateSongList()
    fun updateSongListWithFavorites()
    suspend fun getSongFromRepository(): Resource<List<Song>>
    fun onAddSong(title: String, author: String, url: String)
    suspend fun createNewSong(song: Song): Resource<Int>
    fun onSongUpdate(idSong: Int, title: String, author: String, url: String)
    suspend fun updateSong(idSong: Int, song: Song): Resource<Int>
    fun onDeleteSong(id: Int)
    suspend fun deleteSong(id: Int): Resource<Int>
    fun onGetPlaylistSongs(author: String)
    suspend fun getSongByAuthorFromRepository(author: String): Resource<List<Song>>
    fun onGetFilteredSongs(author: String)
    fun filterSongsByAuthor(songs: List<Song>, author: String): List<Song>
    fun getFavoriteSongs()
    suspend fun obtainFavoriteSongs(): Resource<List<Song>>
    fun onCreateFavorite(idSong: Int)
    fun onCreatePlaylistFavorite(idSong: Int)
    suspend fun createFavorite(idSong: Int): Resource<Int>
    fun onDeleteFavorite(idSong: Int)
    fun onDeletePlaylistFavorite(idSong: Int)
    suspend fun deleteFavorite(idSong: Int): Resource<Int>
    fun updateFavoriteSongList()
}