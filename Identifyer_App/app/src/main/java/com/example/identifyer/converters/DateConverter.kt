package com.example.identifyer.converters

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*


//https://stackoverflow.com/questions/49551461/how-can-i-convert-a-long-value-to-date-time-and-convert-current-time-to-long-kot

public class DateConverter {

    fun longToDate (dateLong :Long):String{
        val date = Date(dateLong)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }

    fun currentTimeToLong(): Long{
        return System.currentTimeMillis()
    }

    fun convertDateToLong (date: String):Long{
        val df = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return df.parse(date).time
    }
}