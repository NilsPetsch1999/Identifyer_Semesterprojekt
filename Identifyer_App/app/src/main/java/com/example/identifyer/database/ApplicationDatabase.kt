package com.example.identifyer.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.identifyer.dao.InmateDao
import com.example.identifyer.dao.RoomDao
import com.example.identifyer.dao.UserDao
import com.example.identifyer.converters.ListConverter
import com.example.identifyer.model.Inmate
import com.example.identifyer.model.Room
import com.example.identifyer.model.User

@Database(entities = [User::class, Room::class, Inmate::class], version = 4)
@TypeConverters(ListConverter::class)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun roomDao() : RoomDao
    abstract fun inmateDao() : InmateDao

    companion object{

        @Volatile
        private var INSTANCE : ApplicationDatabase? = null

        fun getInstance(context: Context): ApplicationDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context) =
            androidx.room.Room.databaseBuilder(context, ApplicationDatabase::class.java, "myDb")
                .build()

        fun getDatabase(context: Context): ApplicationDatabase {
            val instance = androidx.room.Room.databaseBuilder(context.applicationContext,
                ApplicationDatabase::class.java, "identifyer").build()
            INSTANCE =instance
            return instance
        }

    }
}