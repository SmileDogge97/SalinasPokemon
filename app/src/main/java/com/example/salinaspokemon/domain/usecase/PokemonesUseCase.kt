package com.example.salinaspokemon.domain.usecase

import com.example.salinaspokemon.domain.repository.PokemonesRepository
import com.example.salinaspokemon.framework.data.model.pokemones.ResponsePokemones
import com.example.salinaspokemon.framework.usecase.UseCase
import retrofit2.Response
import javax.inject.Inject

class PokemonesUseCase @Inject constructor(private val pokemonesRepository: PokemonesRepository) :
    UseCase<PokemonesUseCase.Params, Response<ResponsePokemones>>() {

    override suspend fun request(params: Params): Response<ResponsePokemones> {
        return params.let {
            pokemonesRepository.attemptLoadPokemones(params.limit)
        }
    }

    data class Params(
        val limit: Int,
    )
}