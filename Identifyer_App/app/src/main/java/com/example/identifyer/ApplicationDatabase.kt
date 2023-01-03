package com.example.identifyer

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.identifyer.converters.ListConverter

@Database(entities = [User::class, Room::class, Inmate::class], version = 1)
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
            androidx.room.Room.databaseBuilder(context, ApplicationDatabase::class.java, "userdb")
                .build()

        fun getDatabase(context: Context): ApplicationDatabase{
            val instance = androidx.room.Room.databaseBuilder(context.applicationContext,
                ApplicationDatabase::class.java, "entry").build()
            INSTANCE =instance
            return instance
        }

    }
}