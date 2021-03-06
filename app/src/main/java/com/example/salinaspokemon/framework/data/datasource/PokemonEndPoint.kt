package com.example.salinaspokemon.framework.data.datasource

import com.example.salinaspokemon.framework.data.model.habilidades.ResponseHabilidades
import com.example.salinaspokemon.framework.data.model.lineaevolutiva.ResponseLineaEvolutiva
import com.example.salinaspokemon.framework.data.model.pokemones.ResponsePokemones
import com.example.salinaspokemon.framework.data.model.pokemoninfo.ResponsePokemonInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonEndPoint {
    @GET("/api/v2/pokemon")
    suspend fun getPokemones(
        @Query("limit") limit: Int
    ): Response<ResponsePokemones>

    @GET("/api/v2/pokemon-species/{pokemon}/")
    suspend fun getPokemonInfo(
        @Path("pokemon") pokemon: String
    ): Response<ResponsePokemonInfo>

    @GET("{evolucion}")
    suspend fun getLineaEvolutiva(
        @Path("evolucion") evolucion: String
    ): Response<ResponseLineaEvolutiva>

    @GET("/api/v2/pokemon/{pokemon}/")
    suspend fun getHabilidades(
        @Path("pokemon") pokemon: String
    ): Response<ResponseHabilidades>
}