package com.example.identifyer.model

import androidx.annotation.NonNull
import androidx.room.*
import java.io.Serializable


@Entity(tableName = "room", indices = [Index(value = ["id"], unique = true)])
data class Room(
    @PrimaryKey(autoGenerate = true) @ColumnInfo var  id : Long? =null,
    @ColumnInfo @NonNull var roomNumber: String?,
    @ColumnInfo @NonNull var inmateList: List<String>?,
    @ColumnInfo @NonNull var securityLevel: Int?,
    @ColumnInfo @NonNull var tract: Int?,
    @ColumnInfo @NonNull var maxCapacity: Int?
): Serializable {
    @Ignore
    constructor(roomName:String?, inmateList: List<String>?, securityLevel: Int, tract: Int?, maxCapacity: Int?) :this (null, roomName, inmateList, securityLevel, tract, maxCapacity)
    constructor() : this("",emptyList(), 0, 0, 0)
}