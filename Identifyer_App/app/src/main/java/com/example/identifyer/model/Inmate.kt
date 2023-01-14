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
    @ColumnInfo @NonNull var nationality : String? ,// added
    @ColumnInfo @NonNull var sentence : String? ,
    @ColumnInfo @NonNull var arrivalDate : Long? , //Convert to date String
    @ColumnInfo @NonNull var timeOfSentence : Long? , //Convert to date String
    @ColumnInfo @NonNull var securityLevel : Int? , //maybe enum ?
    @ColumnInfo @NonNull var physicalWellness : String? ,
    @ColumnInfo @NonNull var mentalWellness : String? ,
    //@ColumnInfo @NonNull var offenseList : List<String>? ,
    //@ColumnInfo @NonNull var offenseAccomplices : List<String>? ,
    @ColumnInfo @NonNull var combatExperience : String? ,
    @ColumnInfo @NonNull var additionalNotes : List<String>? ,
    @ColumnInfo @NonNull var room_id : Long? , //Foreign Key to add
) :Serializable {

    @Ignore
    constructor(firstname: String?, lastname: String?,gender:String?, dateOfBirth:Long?,nationality: String?, sentence: String?, arrivalDate : Long?, timeOfSentence : Long? , securityLevel : Int?, physicalWellness : String?, mentalWellness : String?, combatExperience : String?, additionalNotes : List<String>?, room_id : Long? ):this(null, firstname, lastname, gender, dateOfBirth, nationality, sentence, arrivalDate, timeOfSentence, securityLevel, physicalWellness, mentalWellness, combatExperience, additionalNotes, room_id)

    constructor(): this( "", "", "", null, "", "", null,null, null, "", "", "", emptyList(), null )
}