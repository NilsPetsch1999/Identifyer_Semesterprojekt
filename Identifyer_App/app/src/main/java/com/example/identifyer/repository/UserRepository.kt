package com.example.identifyer.repository

import androidx.lifecycle.LiveData
import com.example.identifyer.dao.UserDao
import com.example.identifyer.model.User

class UserRepository(private val userDao: UserDao) {
    val userEntries : LiveData<List<User>> = userDao.users

    suspend fun insert(user: User){
        userDao.insert(user)
    }

    suspend fun delete(user: User){
        userDao.delete(user)
    }

    suspend fun update(user: User){
        userDao.update(user)
    }

     fun getUsers() : List<User>{
        return userDao.getAllUsers()
    }
    //find user
     fun  getUserById(id: Long): User{
        return userDao.findUserById(id)
    }
}