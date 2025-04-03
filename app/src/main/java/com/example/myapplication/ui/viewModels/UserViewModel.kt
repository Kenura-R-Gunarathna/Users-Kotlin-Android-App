package com.example.myapplication.ui.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.database.AppDatabase
import com.example.myapplication.data.entities.User
import com.example.myapplication.data.repository.UserRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        viewModelScope.launch {
            repository.allUsers.collect { _users.value = it }
        }
    }

    fun insertUser(firstName: String, lastName: String) {
        viewModelScope.launch {
            repository.insertUser(User(firstName = firstName, lastName = lastName))
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch { repository.deleteUser(user) }
    }

    fun updateUser(user: User) {
        viewModelScope.launch { repository.updateUser(user) }
    }
}

