package com.example.identifyer.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.identifyer.model.Inmate
import com.example.identifyer.model.Room


@Dao
interface InmateDao {

    @get:Query("SELECT * FROM inmate")
    val inmates : LiveData<List<Inmate>>

    @Insert
    suspend fun insert(inmate: Inmate)

    @Update
    suspend fun update(inmate: Inmate)

    @Delete
    suspend fun delete(inmate: Inmate)

    @Query("SELECT * FROM inmate")
    fun getAllInmates() :List<Inmate>

    @Query("SELECT * FROM inmate WHERE inmate.id = :id")
    fun findInmateById(id :Long): Inmate

    @Query("SELECT * FROM inmate WHERE inmate.room_id = :roomId")
    fun findInmatesByRoomId(roomId :Long): List<Inmate>
}