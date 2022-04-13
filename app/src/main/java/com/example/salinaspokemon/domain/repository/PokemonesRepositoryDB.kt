package com.example.salinaspokemon.domain.repository

import com.example.salinaspokemon.data.datasource.db.Pokemon

interface PokemonesRepositoryDB {
    suspend fun getAllBD(): List<Pokemon>
    suspend fun loadAllByIdBD(id: IntArray): List<Pokemon>
    suspend fun findByNameBD(name: String): Pokemon
    suspend fun countPokemonBD(): Int
    suspend fun insertAllBD(pokemon: Pokemon)
    suspend fun deleteBD(pokemon: Pokemon)
}