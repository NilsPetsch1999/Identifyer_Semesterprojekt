package com.example.identifyer.model

import androidx.annotation.NonNull
import androidx.room.*
import java.io.Serializable

@Entity(tableName = "user", indices = [Index(value = ["username", "password"], unique = true)])
data class User(
    @PrimaryKey (autoGenerate = true) @ColumnInfo var id : Long? =null,
    @ColumnInfo @NonNull var username : String?,
    @ColumnInfo @NonNull var password : String?
    ): Serializable {

    @Ignore
    constructor(username: String?, password: String?):this(null, username, password)

    constructor(): this("", "")
}