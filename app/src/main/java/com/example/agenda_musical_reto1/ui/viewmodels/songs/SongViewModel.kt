package com.example.agenda_musical_reto1.ui.viewmodels.songs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.agenda_musical_reto1.data.Song
import com.example.agenda_musical_reto1.data.repository.SongRepository
import com.example.agenda_musical_reto1.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SongViewModel (private val songRepository: SongRepository) : ViewModel(){

    private val _songs = MutableLiveData<Resource<List<Song>>>()
    val songs: LiveData<Resource<List<Song>>> get() = _songs

    private val _created = MutableLiveData<Resource<Int>>()
    val created: LiveData<Resource<Int>> get() = _created

    private val _updated = MutableLiveData<Resource<Int>>()
    val updated: LiveData<Resource<Int>> get() = _updated

    private val _deleted = MutableLiveData<Resource<Int>>()
    val deleted: LiveData<Resource<Int>> get() = _deleted

    init{
        updateSongList()
    }

    private fun updateSongList(){
        viewModelScope.launch{
            val repoResponse = getSongFromRepository()
            _songs.value = repoResponse
        }
    }
    private suspend fun getSongFromRepository() : Resource<List<Song>>{
        return withContext(Dispatchers.IO){
            songRepository.getSongs()
        }
    }

    fun onAddSong(title: String, author: String, url: String) {
        val newSong = Song(title, author, url)
        viewModelScope.launch {
            _created.value = createNewSong(newSong)
        }
    }

    private suspend fun createNewSong(song: Song): Resource<Int> {
        return withContext(Dispatchers.IO){
            songRepository.createSong(song)
        }
    }
    fun onSongUpdate(idSong: Int, title: String, author: String, url: String) {
        val song = Song(idSong, title, author, url)
        viewModelScope.launch {
            _updated.value = updateSong(idSong, song)
        }
    }
    private suspend fun updateSong(idSong: Int, song: Song): Resource<Int> {
        return withContext(Dispatchers.IO){
            songRepository.updateSong(idSong, song)
        }
    }
    fun onDeleteSong(id: Int) {

        viewModelScope.launch {
            _deleted.value = deleteSong(id)
        }
    }
    private suspend fun deleteSong(id: Int): Resource<Int> {
        return withContext(Dispatchers.IO) {
            songRepository.deleteSong(id)
        }
    }

}
@Suppress("UNCHECKED_CAST")
class SongViewModelFactory(private val songRepository: SongRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return SongViewModel(songRepository) as T
    }
}