package com.example.agenda_musical_reto1.ui.viewmodels.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.agenda_musical_reto1.data.User
import com.example.agenda_musical_reto1.data.repository.UserRepository
import com.example.agenda_musical_reto1.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel (private val userRepository: UserRepository) : ViewModel(){

    private val _users = MutableLiveData<Resource<List<User>>>()
    val users: LiveData<Resource<List<User>>> get() = _users

    private val _created = MutableLiveData<Resource<Integer>>()
    val created: LiveData<Resource<Integer>> get() = _created

    private suspend fun getUserFromRepository(id: Integer) : Resource<List<User>>{
        return withContext(Dispatchers.IO){
            userRepository.getUserById(id)
        }
    }

    fun onUserRegister(idUser: Int, name: String, surname: String, email: String, password: String) {
        val newUser = User(idUser, name, surname, email, password)
        viewModelScope.launch {
            _created.value = createNewUser(newUser)
        }
    }

    private suspend fun createNewUser(user: User): Resource<Integer> {
        return withContext(Dispatchers.IO){
            userRepository.createUser(user)
        }
    }
    class SongViewModelFactory(private val userRepository: UserRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            return UserViewModel(userRepository) as T
        }
    }
}