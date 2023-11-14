package com.example.agenda_musical_reto1.ui.viewmodels.songs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.data.repository.ISongRepository
import com.example.agenda_musical_reto1.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SongViewModel(private val songRepository: ISongRepository) : ViewModel(), SongViewModelInterface {

    private val _songs = MutableLiveData<Resource<List<Song>>>()
    override val songs: LiveData<Resource<List<Song>>> get() = _songs

    private val _filteredSongs = MutableLiveData<Resource<List<Song>>>()
    override val filteredSongs: LiveData<Resource<List<Song>>> get() = _filteredSongs

    private val _playlistSongs = MutableLiveData<Resource<List<Song>>>()
    override val playlistsongs: LiveData<Resource<List<Song>>> get() = _playlistSongs

    private val _created = MutableLiveData<Resource<Int>?>()
    override val created: MutableLiveData<Resource<Int>?> get() = _created

    private val _updated = MutableLiveData<Resource<Int>?>()
    override val updated: MutableLiveData<Resource<Int>?> get() = _updated

    private val _deleted = MutableLiveData<Resource<Int>?>()
    override val deleted: MutableLiveData<Resource<Int>?> get() = _deleted


    override fun updateSongList(){
        viewModelScope.launch{
            val repoResponse = getSongFromRepository()
            _songs.value = repoResponse
        }
    }
    override suspend fun getSongFromRepository() : Resource<List<Song>>{
        return withContext(Dispatchers.IO){
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
        return withContext(Dispatchers.IO){
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
        return withContext(Dispatchers.IO){
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
//Carga de Playlist
    override fun onGetPlaylistSongs(author: String) {
        if (author.isNotEmpty()) {
            viewModelScope.launch {
                val repoPlaylistResponse = getSongByAuthorFromRepository(author)
                _playlistSongs.value = repoPlaylistResponse
            }
        }
    }
    override suspend fun getSongByAuthorFromRepository(author: String) : Resource<List<Song>>{
        return withContext(Dispatchers.IO){
            songRepository.getSongByAuthor(author)
        }
    }
    //filtrado de canciones por autor
    override fun onGetFilteredSongs(author: String) {
        viewModelScope.launch {
            val currentSongs = _songs.value?.data
            if (currentSongs != null) {
                val filteredSongs = filterSongsByAuthor(currentSongs, author)
                _filteredSongs.value = Resource.success(filteredSongs)
            }
        }
    }
    private fun filterSongsByAuthor(songs: List<Song>, author: String): List<Song> {
        return if (author.isNotEmpty()) {
            songs.filter { it.author.contains(author, ignoreCase = true) }
        } else {
            // Si el término de búsqueda está vacío, simplemente devuelve la lista completa
            songs
        }
    }



}
class SongViewModelFactory(private val songRepository: ISongRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return SongViewModel(songRepository) as T
    }
}