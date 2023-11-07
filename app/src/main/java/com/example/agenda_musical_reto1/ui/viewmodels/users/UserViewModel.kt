package com.example.agenda_musical_reto1.ui.viewmodels.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.agenda_musical_reto1.data.AuthLoginRequest
import com.example.agenda_musical_reto1.data.AuthUpdatePassword
import com.example.agenda_musical_reto1.data.User
import com.example.agenda_musical_reto1.data.repository.IUserRepository
import com.example.agenda_musical_reto1.utils.MyApp
import com.example.agenda_musical_reto1.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(private val userRepository: IUserRepository) : ViewModel(),
    UserViewModelInterface {

    private val _user = MutableLiveData<Resource<User>>()
    override val user: LiveData<Resource<User>> get() = _user

    private val _created = MutableLiveData<Resource<Int>?>()
    override val created: MutableLiveData<Resource<Int>?> get() = _created

    private val _updated = MutableLiveData<Resource<Int>?>()
    override val updated: MutableLiveData<Resource<Int>?> get() = _updated

    private val _deleted = MutableLiveData<Resource<Int>?>()
    override val deleted: MutableLiveData<Resource<Int>?> get() = _deleted
    override suspend fun getUserLogin(email: String, password: String): Resource<String> {
        val authLoginRequest = AuthLoginRequest(email, password)
        return withContext(Dispatchers.IO) {
            userRepository.getUserLogin(authLoginRequest)
        }
    }

    override fun onUserRegister(name: String, surname: String, email: String, password: String) {
        val newUser = User(name, surname, email, password)
        viewModelScope.launch {
            _created.value = registerUser(newUser)
        }
    }

    override suspend fun registerUser(user: User): Resource<Int> {
        return withContext(Dispatchers.IO) {
            userRepository.registerUser(user)
        }
    }

    override fun onUserUpdate(oldPassword: String, newPassword: String) {
        val authUpdatePassword = AuthUpdatePassword(oldPassword, newPassword)
        viewModelScope.launch {
            _updated.value = updateUserPassword(authUpdatePassword)
        }
    }

    override suspend fun updateUserPassword(authUpdatePassword: AuthUpdatePassword): Resource<Int> {
        return withContext(Dispatchers.IO) {
            userRepository.updateUserPassword(authUpdatePassword)
        }
    }

    override fun onDeleteUser() {
        viewModelScope.launch {
            _deleted.value = deleteUser()
        }
    }

    override suspend fun deleteUser(): Resource<Int> {
        return withContext(Dispatchers.IO) {
            userRepository.deleteUser()
        }
    }
}

@Suppress("UNCHECKED_CAST")
class UserViewModelFactory(private val userRepository: IUserRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return UserViewModel(userRepository) as T
    }
}