package com.example.agenda_musical_reto1.ui.viewmodels.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.agenda_musical_reto1.data.AuthUpdatePassword
import com.example.agenda_musical_reto1.data.User
import com.example.agenda_musical_reto1.utils.Resource

interface UserViewModelInterface {
    val user: LiveData<Resource<User>>
    val created: MutableLiveData<Resource<Int>?>
    val updated: MutableLiveData<Resource<Int>?>
    val deleted: MutableLiveData<Resource<Int>?>
    suspend fun getUserLogin(email: String, password: String) : Resource<String>
    fun onUserRegister(name: String, surname: String, email: String, password: String)
    suspend fun registerUser(user: User): Resource<Int>
    fun onUserUpdate(oldPassword: String, newPassword: String)
    suspend fun updateUserPassword(authUpdatePassword: AuthUpdatePassword): Resource<Int>
    fun onDeleteUser()
    suspend fun deleteUser(): Resource<Int>

}