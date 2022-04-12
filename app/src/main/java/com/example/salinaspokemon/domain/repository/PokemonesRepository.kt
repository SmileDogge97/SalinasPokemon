package com.example.salinaspokemon.domain.repository

import com.example.salinaspokemon.framework.data.model.ResponsePokemones
import retrofit2.Response

interface PokemonesRepository {
    suspend fun attemptLoadPokemones(limit: Int) : Response<ResponsePokemones>
}