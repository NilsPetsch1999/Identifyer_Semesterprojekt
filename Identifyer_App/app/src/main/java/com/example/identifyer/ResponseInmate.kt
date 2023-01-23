package com.example.identifyer

import java.io.Serializable

data class ResponseInmate(
        var id : Long,
        var firstName: String,
        var lastName: String,
        var gender: String,
        var dateOfBirth: Long,
        var nationality: List<String>,
        var sentence : Long,
        var arrivalDate : Long,
        var securityLevel: String,
        var physicalWellness : String,
        var mentalWellness : String,
        var offenseList: List<Offense>,
        var combatExperience: List<String>,
        var notes : List<String>,
        var room : ResponseRoom,
) : Serializable{


}
