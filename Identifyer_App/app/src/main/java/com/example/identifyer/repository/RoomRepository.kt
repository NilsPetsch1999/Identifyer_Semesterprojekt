package com.example.identifyer.repository

import androidx.lifecycle.LiveData
import com.example.identifyer.dao.RoomDao
import com.example.identifyer.model.Room

class RoomRepository(private val roomDao: RoomDao) {
    val roomEntries :LiveData<List<Room>> = roomDao.rooms

    suspend fun insert (room: Room){
        roomDao.insert(room)
    }

    suspend fun delete(room: Room){
        roomDao.delete(room)
    }

    suspend fun update(room: Room){
        roomDao.update(room)
    }
    fun getAllRooms(): List<Room>{
        return roomDao.getAllRooms()
    }

    fun getRoomById (id: Long) :Room{
        return roomDao.findRoomById(id)
    }


}