package com.example.salinaspokemon.data.datasource.db

import javax.inject.Inject

class PokemonesDataSourceBDImpl @Inject constructor(
    private val pokemonDao: PokemonDao
):PokemonesDataSourceBD {

    override suspend fun getPokemonesDB(): List<Pokemon> {
        return pokemonDao.getAll()
    }

    override suspend fun loadByIdBD(id: IntArray): List<Pokemon> {
        return pokemonDao.loadAllByIds(id)
    }

    override suspend fun findByNameBD(name: String): Pokemon {
        return pokemonDao.findByName(name)
    }

    override suspend fun countPokemonBD(): Int {
        return pokemonDao.countPokemon()
    }

    override suspend fun insertBD(pokemones: Pokemon) {
        return pokemonDao.insertAll(pokemones)
    }

    override suspend fun deleteBD(pokemon: Pokemon) {
        return pokemonDao.delete(pokemon)
    }
}