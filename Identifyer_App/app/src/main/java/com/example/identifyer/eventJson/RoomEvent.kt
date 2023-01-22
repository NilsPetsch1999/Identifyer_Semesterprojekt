package com.example.identifyer.eventJson

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

class RoomEvent(
    val  id : Long?,
    val roomNumber: String?,
    val securityLevel:Int?,
    val inmateList : List<String>?,
    val maxCapacity : Int?,
    val tract : Int?) {




}