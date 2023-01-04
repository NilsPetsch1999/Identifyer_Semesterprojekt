package com.example.identifyer.model

import androidx.annotation.NonNull
import androidx.room.*


@Entity(tableName = "room", indices = [Index(value = ["securityLevel"], unique = true)])
data class Room(
    @PrimaryKey(autoGenerate = true) @ColumnInfo var  id : Long? =null,
    @ColumnInfo @NonNull var roomName: String?,
    @ColumnInfo @NonNull var inmateList: List<String>?,
    @ColumnInfo @NonNull var securityLevel: Int?
) {
    @Ignore
    constructor(roomName:String?, inmateList: List<String>?, securityLevel: Int) :this (null, roomName, inmateList, securityLevel)
    constructor() : this("",emptyList(), 0)
}