package com.example.agenda_musical_reto1.ui.viewmodels.songs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.data.repository.ISongRepository
import com.example.agenda_musical_reto1.data.repository.IUserRepository
import com.example.agenda_musical_reto1.data.repository.remote.RemoteSongDataSource
import com.example.agenda_musical_reto1.data.repository.remote.UserRepository
import com.example.agenda_musical_reto1.utils.MyApp
import com.example.agenda_musical_reto1.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class SongViewModel(
    private val songRepository: ISongRepository,
    private val userRepository: IUserRepository
) : ViewModel(), SongViewModelInterface {

    private val _songs = MutableLiveData<Resource<List<Song>>>()
    override val songs: LiveData<Resource<List<Song>>> get() = _songs

    private val _created = MutableLiveData<Resource<Int>?>()
    override val created: MutableLiveData<Resource<Int>?> get() = _created

    private val _updated = MutableLiveData<Resource<Int>?>()
    override val updated: MutableLiveData<Resource<Int>?> get() = _updated

    private val _deleted = MutableLiveData<Resource<Int>?>()
    override val deleted: MutableLiveData<Resource<Int>?> get() = _deleted

    private val _filteredSongs = MutableLiveData<Resource<List<Song>>>()
    override val filteredSongs: LiveData<Resource<List<Song>>> get() = _filteredSongs

    private val _favoriteSongs = MutableLiveData<Resource<List<Song>>>()
    override val favoriteSongs: LiveData<Resource<List<Song>>> get() = _favoriteSongs

    private val _createdFavorite = MutableLiveData<Resource<Int>?>()
    override val createdFavorite: LiveData<Resource<Int>?> get() = _createdFavorite

    private val _deletedFavorite = MutableLiveData<Resource<Int>?>()
    override val deletedFavorite: LiveData<Resource<Int>?> get() = _deletedFavorite

    init {
        runBlocking {
            val job: Job = launch(context = Dispatchers.Default) {
                updateSongList()
            }

            if (MyApp.userPreferences.getLoggedUser() != null) {
                getFavoriteSongs()
            }
            job.join()
        }
    }


    override fun updateSongList() {
        viewModelScope.launch {
            val repoResponse = getSongFromRepository()
            _songs.value = repoResponse
        }
    }

    override suspend fun getSongFromRepository(): Resource<List<Song>> {
        return withContext(Dispatchers.IO) {
            songRepository.getSongs()
        }
    }

    override fun onAddSong(title: String, author: String, url: String) {
        val newSong = Song(title, author, url)
        viewModelScope.launch {
            _created.value = createNewSong(newSong)
        }
    }

    override suspend fun createNewSong(song: Song): Resource<Int> {
        return withContext(Dispatchers.IO) {
            songRepository.createSong(song)
        }
    }

    override fun onSongUpdate(idSong: Int, title: String, author: String, url: String) {
        val song = Song(idSong, title, author, url)
        viewModelScope.launch {
            _updated.value = updateSong(idSong, song)
        }
    }

    override suspend fun updateSong(idSong: Int, song: Song): Resource<Int> {
        return withContext(Dispatchers.IO) {
            songRepository.updateSong(idSong, song)
        }
    }

    override fun onDeleteSong(id: Int) {

        viewModelScope.launch {
            _deleted.value = deleteSong(id)
        }
    }

    override suspend fun deleteSong(id: Int): Resource<Int> {
        return withContext(Dispatchers.IO) {
            songRepository.deleteSong(id)
        }
    }

    override fun onGetFilteredSongs(author: String) {
        if (author.isNotEmpty()) {
            viewModelScope.launch {
                val repoFilteredResponse = getSongByAuthorFromRepository(author)
                _filteredSongs.value = repoFilteredResponse
            }
        }
    }

    override suspend fun getSongByAuthorFromRepository(author: String): Resource<List<Song>> {
        return withContext(Dispatchers.IO) {
            songRepository.getSongByAuthor(author)
        }
    }

    fun getFavoriteSongs() {
        viewModelScope.launch {
            val repoResponse = obtainFavoriteSongs()
            _favoriteSongs.value = repoResponse
        }
    }

    override suspend fun obtainFavoriteSongs(): Resource<List<Song>> {
        return withContext(Dispatchers.IO) {
            userRepository.getAllFavorites()
        }
    }

    override fun onCreateFavorite(idSong: Int) {
        viewModelScope.launch {
            _createdFavorite.value = createFavorite(idSong)
            _favoriteSongs.value?.data?.map {
                if (it.idSong == idSong) {
                    it.isFavorite = true
                }
            }

            _favoriteSongs.value = _favoriteSongs.value
        }
    }

    override suspend fun createFavorite(idSong: Int): Resource<Int> {
        return withContext(Dispatchers.IO) {
            userRepository.createFavorite(idSong)
        }
    }

    override fun onDeleteFavorite(idSong: Int) {
        viewModelScope.launch {
            _deletedFavorite.value = deleteFavorite(idSong)
            _favoriteSongs.value?.data?.map {
                if (it.idSong == idSong) {
                    it.isFavorite = false
                }
            }
        }
    }

    override suspend fun deleteFavorite(idSong: Int): Resource<Int> {
        return withContext(Dispatchers.IO) {
            userRepository.deleteFavorite(idSong)
        }
    }

}

class SongViewModelFactory(
    private val songRepository: ISongRepository,
    private val userRepository: IUserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return SongViewModel(songRepository, userRepository) as T
    }
}