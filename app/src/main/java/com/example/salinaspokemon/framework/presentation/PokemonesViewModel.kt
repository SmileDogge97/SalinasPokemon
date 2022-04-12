package com.example.salinaspokemon.framework.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salinaspokemon.domain.usecase.PokemonesUseCase
import com.example.salinaspokemon.framework.data.model.ResponsePokemones
import com.example.salinaspokemon.framework.presentation.viewstate.PokemonesViewState
import com.example.salinaspokemon.utils.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PokemonesViewModel @Inject constructor(
    private val pokemonesUseCase: UseCase<PokemonesUseCase.Params, Response<ResponsePokemones>>
) : ViewModel() {
    private var _pokemonesState = MutableLiveData<PokemonesViewState>()
    val pokemonesState: LiveData<PokemonesViewState> get() = _pokemonesState

    fun loadPokemones() {
        _pokemonesState.postValue(PokemonesViewState.Loadig)
        viewModelScope.launch {
            Log.d("PokemonesViewModel", "loadPokemones")
            val pokemonesResult = runCatching {
                pokemonesUseCase.execute(PokemonesUseCase.Params(151))
            }
            pokemonesResult.onSuccess { pokemones ->
                val totalPokemones = pokemones.body()?.results.orEmpty()
                Log.d("PokemonesViewModel/loadPokemones/totalPokemones", pokemones.body()!!.results.toString())
                if (totalPokemones.isNotEmpty()){
                    _pokemonesState.postValue(PokemonesViewState.Success(pokemones.body()!!.results))
                } else {
                    _pokemonesState.postValue(PokemonesViewState.PokemonesNotFound)
                }
            }
            pokemonesResult.onFailure {

                _pokemonesState.postValue(PokemonesViewState.Error(it.localizedMessage.orEmpty()))
            }
        }
    }
}