package com.example.salinaspokemon.utils

abstract class UseCase<Params, ReturnValue> {
    abstract suspend fun execute(params: Params):ReturnValue
}