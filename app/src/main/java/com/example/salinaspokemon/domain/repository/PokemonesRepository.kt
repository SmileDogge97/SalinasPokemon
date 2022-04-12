package com.example.salinaspokemon.domain.repository

import com.example.salinaspokemon.data.datasource.db.Pokemon
import com.example.salinaspokemon.framework.data.model.ResponsePokemones
import retrofit2.Response

interface PokemonesRepository {
    suspend fun attemptLoadPokemones(limit: Int) : Response<ResponsePokemones>
    suspend fun getAllBDPokemones(): List<Pokemon>
    suspend fun loadAllByIdBDPokemones(id: IntArray): List<Pokemon>
    suspend fun findByNameBDPokemones(name: String): Pokemon
    suspend fun countPokemonBD(): Int
    suspend fun insertAllBD(pokemon: Pokemon)
    suspend fun deleteBD(pokemon: Pokemon)
}