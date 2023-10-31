package com.example.agenda_musical_reto1.ui.viewmodels.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.agenda_musical_reto1.data.User
import com.example.agenda_musical_reto1.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface UserViewModelInterface {
    val users: LiveData<Resource<List<User>>>
    val created: LiveData<Resource<Integer>>
    val updated: LiveData<Resource<Integer>>
    val deleted: LiveData<Resource<Integer>>
    suspend fun getUserById(id: Integer) : Resource<List<User>>
    fun onUserRegister(name: String, surname: String, email: String, password: String)
    suspend fun createNewUser(user: User): Resource<Integer>
    fun onUserUpdate(idUser: Int, name: String, surname: String, email: String, password: String)
    suspend fun updateUser(idUser: Int, user: User): Resource<Integer>
    fun onDeleteUser(id: Int)
    suspend fun deleteUser(id: Int): Resource<Integer>

}