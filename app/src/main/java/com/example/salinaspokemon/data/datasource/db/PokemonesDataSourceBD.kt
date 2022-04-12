package com.example.salinaspokemon.data.datasource.db

interface PokemonesDataSourceBD {
    suspend fun getPokemonesDB(): List<Pokemon>
    suspend fun loadByIdBD(id: IntArray): List<Pokemon>
    suspend fun findByNameBD(name: String): Pokemon
    suspend fun countPokemonBD(): Int
    suspend fun insertBD(pokemones: Pokemon)
    suspend fun deleteBD(pokemon: Pokemon)
}