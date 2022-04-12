package com.example.salinaspokemon.data.datasource.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.salinaspokemon.data.datasource.db.Pokemon

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon")
    suspend fun getAll(): List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE url IN (:pokemonUrls)")
    suspend fun loadAllByIds(pokemonUrls: IntArray): List<Pokemon>

    @Query ("SELECT * FROM pokemon WHERE name LIKE :name LIMIT 1")
    suspend fun findByName(name: String): Pokemon

    @Query("Select COUNT(*) FROM pokemon")
    suspend fun countPokemon(): Int

    @Insert
    suspend fun insertAll(vararg pokemones: Pokemon)

    @Delete
    suspend fun delete(pokemon: Pokemon)
}