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

    private val _user = MutableLiveData<Resource<User>>()
    val user: LiveData<Resource<User>> get() = _user

    private val _created = MutableLiveData<Resource<Int>>()
    val created: LiveData<Resource<Int>> get() = _created

    private val _updated = MutableLiveData<Resource<Int>>()
    val updated: LiveData<Resource<Int>> get() = _updated

    private val _deleted = MutableLiveData<Resource<Int>>()
    val deleted: LiveData<Resource<Int>> get() = _deleted



    private suspend fun getUserById(id: Int) : Resource<User>{
        return withContext(Dispatchers.IO){
            userRepository.getUserById(id)
        }
    }
    fun onUserRegister(name: String, surname: String, email: String, password: String) {
        val newUser = User(name, surname, email, password)
        viewModelScope.launch {
            _created.value = createNewUser(newUser)
        }
    }
    private suspend fun createNewUser(user: User): Resource<Int> {
        return withContext(Dispatchers.IO){
            userRepository.createUser(user)
        }
    }
    fun onUserUpdate(idUser: Int, name: String, surname: String, email: String, password: String) {
        val user = User(idUser, name, surname, email, password)
        viewModelScope.launch {
            _updated.value = updateUser(idUser, user)
        }
    }
    private suspend fun updateUser(idUser: Int, user: User): Resource<Int> {
        return withContext(Dispatchers.IO){
            userRepository.updateUser(idUser, user)
        }
    }
    fun onDeleteUser(id: Int) {

        viewModelScope.launch {
            _deleted.value = deleteUser(id)
        }
    }
    private suspend fun deleteUser(id: Int): Resource<Int> {
        return withContext(Dispatchers.IO) {
            userRepository.deleteUser(id)
        }
    }
}
@Suppress("UNCHECKED_CAST")
class UserViewModelFactory(private val userRepository: UserRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return UserViewModel(userRepository) as T
    }
}