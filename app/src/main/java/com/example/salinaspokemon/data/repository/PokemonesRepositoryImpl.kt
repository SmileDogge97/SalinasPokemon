package com.example.salinaspokemon.data.repository

import com.example.salinaspokemon.data.datasource.PokemonesDataSource
import com.example.salinaspokemon.domain.repository.PokemonesRepository
import com.example.salinaspokemon.framework.data.model.ResponsePokemones
import retrofit2.Response
import javax.inject.Inject

class PokemonesRepositoryImpl @Inject constructor(
    private val dataSource: PokemonesDataSource
) :
    PokemonesRepository {
    override suspend fun attemptLoadPokemones(limit: Int): Response<ResponsePokemones> =
        dataSource.getPokemones(limit)
}