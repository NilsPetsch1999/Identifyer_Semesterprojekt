package com.example.identifyer.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.identifyer.model.Room
import com.example.identifyer.model.User

@Dao
interface UserDao {

    @get:Query("SELECT * FROM user")
    val users : LiveData<List<User>>

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM user")
    fun getAllUsers() :List<User>

    @Query("SELECT * FROM user WHERE user.id = :id")
    fun findUserById(id :Long): User
}
