package com.example.identifyer

import androidx.annotation.NonNull
import androidx.room.*


@Entity(tableName = "room", indices = [Index(value = ["inmateList", "securityLevel"], unique = true)])
data class Room(
    @PrimaryKey(autoGenerate = true) @ColumnInfo var  id : Long? =null,
    @ColumnInfo @NonNull var inmateList: List<String>? ,
    @ColumnInfo @NonNull var securityLevel: Int?
) {
    @Ignore
    constructor(inmateList: List<String>?, securityLevel: Int) :this (null, inmateList, securityLevel)
    constructor() : this(emptyList(), 0)
}