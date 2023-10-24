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

    private val _created = MutableLiveData<Resource<Integer>>()
    val created: LiveData<Resource<Integer>> get() = _created

    init{
        updateSongList()
    }

    fun updateSongList(){
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

    private suspend fun createNewSong(song: Song): Resource<Integer> {
        return withContext(Dispatchers.IO){
            songRepository.createSong(song)
        }
    }
    class SongViewModelFactory(private val songRepository: SongRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            return SongViewModel(songRepository) as T
        }
    }
}