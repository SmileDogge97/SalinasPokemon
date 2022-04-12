package com.example.salinaspokemon.data.repository

import com.example.salinaspokemon.data.datasource.PokemonesDataSource
import com.example.salinaspokemon.data.datasource.db.Pokemon
import com.example.salinaspokemon.data.datasource.db.PokemonesDataSourceBD
import com.example.salinaspokemon.domain.repository.PokemonesRepository
import com.example.salinaspokemon.framework.data.model.ResponsePokemones
import retrofit2.Response
import javax.inject.Inject

class PokemonesRepositoryImpl @Inject constructor(
    private val dataSource: PokemonesDataSource,
    private val dataSourceBD: PokemonesDataSourceBD
) :
    PokemonesRepository {

    override suspend fun attemptLoadPokemones(limit: Int): Response<ResponsePokemones> =
        dataSource.getPokemones(limit)

    override suspend fun getAllBDPokemones(): List<Pokemon> =
        dataSourceBD.getPokemonesDB()

    override suspend fun loadAllByIdBDPokemones(id: IntArray): List<Pokemon> =
        dataSourceBD.loadByIdBD(id)

    override suspend fun findByNameBDPokemones(name: String): Pokemon =
        dataSourceBD.findByNameBD(name)

    override suspend fun countPokemonBD(): Int =
        dataSourceBD.countPokemonBD()

    override suspend fun insertAllBD(pokemon: Pokemon) = dataSourceBD.insertBD(pokemon)

    override suspend fun deleteBD(pokemon: Pokemon) = dataSourceBD.deleteBD(pokemon)
}