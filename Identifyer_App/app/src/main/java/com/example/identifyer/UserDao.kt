package com.example.identifyer

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @get:Query("SELECT * FROM user")
    val users : LiveData<List<User>>

    @Insert
    suspend fun insert(user:User)

    @Update
    suspend fun update(user:User)

    @Delete
    suspend fun delete(user:User)

    @Query("SELECT * FROM user")
    fun getAllUsers() :List <User>
}