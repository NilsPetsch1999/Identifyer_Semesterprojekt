package com.example.identifyer

import java.io.Serializable

data class Offense(var id: Long, var timeOfOffense: Long, var description: String, ) :
    Serializable {

}