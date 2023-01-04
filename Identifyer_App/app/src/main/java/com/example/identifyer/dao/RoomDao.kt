package com.example.identifyer.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.identifyer.model.Room


@Dao
interface RoomDao {

    @get:Query("SELECT * FROM room")
    val rooms : LiveData<List<Room>>

    @Insert
    suspend fun insert(room: Room)

    @Update
    suspend fun update(room: Room)

    @Delete
    suspend fun delete(room: Room)

    @Query("SELECT * FROM room")
    fun getAllRooms() :List<Room>

    @Query("SELECT * FROM room WHERE room.id = :id")
    fun findRoomById(id :Long): Room
}