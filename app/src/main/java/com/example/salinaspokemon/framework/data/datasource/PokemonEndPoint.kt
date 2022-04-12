package com.example.salinaspokemon.framework.data.datasource

import com.example.salinaspokemon.framework.data.model.ResponsePokemones
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonEndPoint {
    @GET("/api/v2/pokemon")
    suspend fun getPokemones(
        @Query("limit") limit: Int
    ): Response<ResponsePokemones>
}