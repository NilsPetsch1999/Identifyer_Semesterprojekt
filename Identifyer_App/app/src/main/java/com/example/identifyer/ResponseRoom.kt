package com.example.identifyer

import java.io.Serializable

data class ResponseRoom(
    var id : Long?,
    var roomNumber :String?,
    var tractName: String?,
    var maxInmateCount : Int?,
    var securityLevel : String?

) :Serializable   {
    constructor() : this(null, null, "", null, "")

}
