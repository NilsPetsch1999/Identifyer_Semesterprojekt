package com.example.identifyer.model

import androidx.annotation.NonNull
import androidx.room.*
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = "inmate", indices = [Index(value = ["firstname", "lastname"], unique = true)])
data class Inmate(
    @PrimaryKey(autoGenerate = true) @ColumnInfo var id : Long? = null,
    @ColumnInfo @NonNull var firstname : String? ,
    @ColumnInfo @NonNull var lastname : String? ,
    @ColumnInfo @NonNull var gender : String? ,
    @ColumnInfo @NonNull var dateOfBirth : Long? , //Convert to date String
    @ColumnInfo @NonNull var arrivalDate : Long? , //Convert to date String
    @ColumnInfo @NonNull var timeOfSentence : Long? , //Convert to date String
    @ColumnInfo @NonNull var securityLevel : Int? , //maybe enum ?
    @ColumnInfo @NonNull var physicalWellness : String? ,
    @ColumnInfo @NonNull var mentalWellness : String? ,
    @ColumnInfo @NonNull var offenseList : List<String>? , //List of the offenses of a person made
    @ColumnInfo @NonNull var offenseAccomplices : List<String>? , // inmates or all  known accomplices  ?
    @ColumnInfo @NonNull var combatExperience : String? ,
    @ColumnInfo @NonNull var additionalNotes : List<String>? ,
    @ColumnInfo @NonNull var room_id : Long? , //Foreign Key to add
) :Serializable {

    @Ignore
    constructor(firstname: String?, lastname: String?,gender:String?, dateOfBirth:Long?, arrivalDate : Long?, timeOfSentence : Long? , securityLevel : Int?, physicalWellness : String?, mentalWellness : String?, offenseList : List<String>?, offenseAccomplices : List<String>?, combatExperience : String?, additionalNotes : List<String>?, room_id : Long? ):this(null, firstname, lastname, gender, dateOfBirth, arrivalDate, timeOfSentence, securityLevel, physicalWellness, mentalWellness, offenseList, offenseAccomplices, combatExperience, additionalNotes, room_id)

    constructor(): this( "", "", "", null, null, null, null,"", "", emptyList(), emptyList(), "", emptyList(), null )
}