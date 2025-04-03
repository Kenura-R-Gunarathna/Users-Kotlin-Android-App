package com.example.myapplication.data.repository

import com.example.myapplication.data.dao.UserDao
import com.example.myapplication.data.entities.User

class UserRepository(private val userDao: UserDao) {

    val allUsers = userDao.getAll()

    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    suspend fun insertUsers(users: List<User>) {
        userDao.insertAll(*users.toTypedArray())
    }

    suspend fun deleteUser(user: User) {
        userDao.delete(user)
    }

    suspend fun updateUser(user: User) {
        userDao.update(user)
    }
}