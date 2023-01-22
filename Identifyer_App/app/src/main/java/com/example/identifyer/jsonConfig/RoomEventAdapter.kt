package com.example.identifyer.jsonConfig

import com.example.identifyer.eventJson.RoomEvent
import com.example.identifyer.model.Room
/*
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class RoomEventAdapter {

    @FromJson
    fun eventFromJson(eventRoom: RoomEvent): Room {
        val room : Room =  Room();
        room.id = eventRoom.id;
        room.roomNumber = eventRoom.roomNumber;
        room.tract = eventRoom.tract;
        room.inmateList = eventRoom.inmateList;
        room.securityLevel = eventRoom.securityLevel
        room.maxCapacity = eventRoom.maxCapacity
        return room
    }

    @ToJson
    fun eventToJson(room: Room): RoomEvent {
        val roomEvent : RoomEvent = RoomEvent(room.id,room.roomNumber, room.securityLevel, room.inmateList, room.maxCapacity, room.tract)
        return roomEvent
    }
}*/