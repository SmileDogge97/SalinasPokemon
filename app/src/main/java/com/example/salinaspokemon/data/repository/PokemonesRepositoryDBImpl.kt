package com.example.salinaspokemon.data.repository

import com.example.salinaspokemon.data.datasource.db.Pokemon
import com.example.salinaspokemon.data.datasource.db.PokemonesDataSourceBD
import com.example.salinaspokemon.domain.repository.PokemonesRepositoryDB
import javax.inject.Inject

class PokemonesRepositoryDBImpl @Inject constructor(
    private val dataSourceBD: PokemonesDataSourceBD
) :
    PokemonesRepositoryDB {
    override suspend fun getAllBD(): List<Pokemon> =
        dataSourceBD.getPokemonesDB()

    override suspend fun loadAllByIdBD(id: IntArray): List<Pokemon> =
        dataSourceBD.loadByIdBD(id)

    override suspend fun findByNameBD(name: String): Pokemon =
        dataSourceBD.findByNameBD(name)

    override suspend fun countPokemonBD(): Int =
        dataSourceBD.countPokemonBD()

    override suspend fun insertAllBD(pokemon: Pokemon) = dataSourceBD.insertBD(pokemon)

    override suspend fun deleteBD(pokemon: Pokemon) = dataSourceBD.deleteBD(pokemon)
}