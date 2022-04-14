package com.example.salinaspokemon.domain.repository

import com.example.salinaspokemon.framework.data.model.lineaevolutiva.ResponseLineaEvolutiva
import com.example.salinaspokemon.framework.data.model.pokemones.ResponsePokemones
import com.example.salinaspokemon.framework.data.model.pokemoninfo.ResponsePokemonInfo
import retrofit2.Response

interface PokemonesRepository {
    suspend fun attemptLoadPokemones(limit: Int) : Response<ResponsePokemones>
    suspend fun attemptLoadPokemonInfo(pokemon: String): Response<ResponsePokemonInfo>
    suspend fun attemptLoadPokemonLineEvo(url: String): Response<ResponseLineaEvolutiva>
}