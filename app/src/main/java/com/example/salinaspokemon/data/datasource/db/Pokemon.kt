package com.example.salinaspokemon.data.datasource.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class Pokemon(
    @PrimaryKey val url: String,
    @ColumnInfo(name = "name") val name: String?
)

