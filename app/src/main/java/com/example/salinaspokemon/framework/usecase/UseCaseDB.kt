package com.example.salinaspokemon.framework.usecase

import com.example.salinaspokemon.data.datasource.db.Pokemon

interface UseCaseDB {
    suspend fun getAllBD(): List<Pokemon>
    suspend fun loadAllByIdsBD(id: IntArray): List<Pokemon>
    suspend fun findByNameBD(name: String): Pokemon
    suspend fun countPokemonBD(): Int
    suspend fun insertAllBD(pokemon: Pokemon)
    suspend fun deleteBD(pokemon: Pokemon)
}