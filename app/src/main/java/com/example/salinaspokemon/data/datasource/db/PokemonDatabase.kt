package com.example.salinaspokemon.data.datasource.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.salinaspokemon.data.datasource.db.Pokemon
import com.example.salinaspokemon.data.datasource.db.PokemonDao

@Database(entities = [Pokemon::class], version = 1)
abstract class PokemonDatabase : RoomDatabase() {
    companion object {
        fun create(context: Context): PokemonDatabase {
            return Room.databaseBuilder(context, PokemonDatabase::class.java, "pokemones").build()
        }
    }

    abstract fun pokemonDao(): PokemonDao
}