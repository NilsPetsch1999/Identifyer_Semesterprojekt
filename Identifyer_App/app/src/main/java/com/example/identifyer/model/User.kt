package com.example.identifyer.model

import androidx.annotation.NonNull
import androidx.room.*

@Entity(tableName = "user", indices = [Index(value = ["username", "password"], unique = true)])
data class User(
    @PrimaryKey (autoGenerate = true) @ColumnInfo var id : Long? =null,
    @ColumnInfo @NonNull var username : String?,
    @ColumnInfo @NonNull var password : String?
    ) {

    @Ignore
    constructor(username: String?, password: String?):this(null, username, password)

    constructor(): this("", "")
}