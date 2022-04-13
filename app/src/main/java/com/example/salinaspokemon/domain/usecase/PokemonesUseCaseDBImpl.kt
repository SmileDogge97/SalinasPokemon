package com.example.salinaspokemon.domain.usecase

import com.example.salinaspokemon.data.datasource.db.Pokemon
import com.example.salinaspokemon.domain.repository.PokemonesRepository
import com.example.salinaspokemon.domain.repository.PokemonesRepositoryDB
import com.example.salinaspokemon.framework.usecase.UseCaseDB
import javax.inject.Inject

class PokemonesUseCaseDBImpl @Inject constructor(
    private val pokemonesRepository: PokemonesRepositoryDB
) :UseCaseDB {

    override suspend fun getAllBD(): List<Pokemon> {
        return pokemonesRepository.getAllBD()
    }

    override suspend fun loadAllByIdsBD(id:IntArray): List<Pokemon> {
        return pokemonesRepository.loadAllByIdBD(id)
    }

    override suspend fun findByNameBD(name: String): Pokemon {
        return pokemonesRepository.findByNameBD(name)
    }

    override suspend fun countPokemonBD(): Int{
        return pokemonesRepository.countPokemonBD()
    }

    override suspend fun insertAllBD(pokemon: Pokemon){
        return pokemonesRepository.insertAllBD(pokemon)
    }

    override suspend fun deleteBD(pokemon: Pokemon){
        return pokemonesRepository.deleteBD(pokemon)
    }
}