package com.example.identifyer.model

import androidx.annotation.NonNull
import androidx.room.*
import java.io.Serializable


@Entity(tableName = "room", indices = [Index(value = ["id"], unique = true)])
data class Room(
    @PrimaryKey @ColumnInfo var  id : Long?,
    @ColumnInfo @NonNull var roomNumber: String?,
    @ColumnInfo @NonNull var inmateList: List<String>?,
    @ColumnInfo @NonNull var securityLevel: Int?,
    @ColumnInfo @NonNull var tract: String?,
    @ColumnInfo @NonNull var maxCapacity: Int?
): Serializable {

    //constructor(roomName:String?, inmateList: List<String>?, securityLevel: Int, tract: String?, maxCapacity: Int?) :this (id, roomName, inmateList, securityLevel, tract, maxCapacity)
    constructor() : this(null, "",emptyList(), 0, "", 0)
}