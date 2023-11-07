package com.example.agenda_musical_reto1.ui.viewmodels.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.agenda_musical_reto1.data.User
import com.example.agenda_musical_reto1.utils.Resource

interface UserViewModelInterface {
    val user: LiveData<Resource<User>>
    val created: MutableLiveData<Resource<Int>?>
    val updated: MutableLiveData<Resource<Int>?>
    val deleted: MutableLiveData<Resource<Int>?>
    suspend fun getUserById(id: Int) : Resource<User>
    fun onUserRegister(name: String, surname: String, email: String, password: String)
    suspend fun createNewUser(user: User): Resource<Int>
    fun onUserUpdate(idUser: Int, name: String, surname: String, email: String, password: String)
    suspend fun updateUser(idUser: Int, user: User): Resource<Int>
    fun onDeleteUser(id: Int)
    suspend fun deleteUser(id: Int): Resource<Int>

}