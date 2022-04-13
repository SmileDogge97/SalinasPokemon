package com.example.salinaspokemon.data.datasource

import com.example.salinaspokemon.framework.data.model.pokemones.ResponsePokemones
import com.example.salinaspokemon.framework.data.model.pokemoninfo.ResponsePokemonInfo
import retrofit2.Response

interface PokemonesDataSource {
    suspend fun getPokemones(limit: Int): Response<ResponsePokemones>
    suspend fun getPokemonInfo(pokemon: String): Response<ResponsePokemonInfo>
}