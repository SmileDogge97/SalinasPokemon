package com.example.salinaspokemon.domain.usecase

import com.example.salinaspokemon.domain.repository.PokemonesRepository
import com.example.salinaspokemon.framework.data.model.ResponsePokemones
import com.example.salinaspokemon.utils.UseCase
import retrofit2.Response
import javax.inject.Inject

class PokemonesUseCase @Inject constructor(private val pokemonesRepository: PokemonesRepository) :
    UseCase<PokemonesUseCase.Params, Response<ResponsePokemones>>() {

    override suspend fun execute(params: Params): Response<ResponsePokemones> {
        params.let {
            return pokemonesRepository.attemptLoadPokemones(params.limit)
        }
    }

    data class Params(
        val limit: Int
    )
}