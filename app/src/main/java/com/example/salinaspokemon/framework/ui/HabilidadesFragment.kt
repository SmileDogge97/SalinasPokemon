package com.example.salinaspokemon.framework.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.salinaspokemon.databinding.FragmentHabilidadesBinding
import com.example.salinaspokemon.framework.data.model.habilidades.Ability
import com.example.salinaspokemon.framework.presentation.PokemonHabilidadesViewModel
import com.example.salinaspokemon.framework.presentation.viewstate.PokemonHabilidadesViewState
import com.example.salinaspokemon.framework.ui.adapter.PokemonHabilidadesAdapter
import com.example.salinaspokemon.utils.ValidarR
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HabilidadesFragment : Fragment() {

    private val pokemonViewModel: PokemonHabilidadesViewModel by viewModels()
    private var _binding: FragmentHabilidadesBinding? = null
    private val binding get() = _binding
    private lateinit var pokemonesAdapter: PokemonHabilidadesAdapter
    private lateinit var contexto: Context
    lateinit var pokemon: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHabilidadesBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contexto = requireContext().applicationContext
        val text = arguments?.getString("pokemon")
        pokemon = text.toString()

        attachObservers()
        bindViews(pokemon)
    }

    private fun attachObservers() {
        pokemonViewModel.pokemonState.observe(viewLifecycleOwner, {
            renderPokemones(it)
        })
    }

    private fun bindViews(pokemon: String): Unit = with(binding) {
        if (ValidarR.hayRed(contexto)) {
            pokemonViewModel.loadHabilidades(pokemon)
        } else {
            displayError("No hay internet banda x.x")
        }

        pokemonesAdapter = PokemonHabilidadesAdapter(onPokemonesSelected = { result ->

        })

        binding?.list?.apply {
            adapter = pokemonesAdapter
            layoutManager = LinearLayoutManager(contexto, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun showLoader() {
        with(binding) {
            this?.txtMessageLineEvo?.visibility = View.GONE
            this?.list?.visibility = View.GONE
            this?.pCargar?.visibility = View.VISIBLE
        }
    }

    private fun renderPokemones(it: PokemonHabilidadesViewState) {
        when (it) {
            is PokemonHabilidadesViewState.Loading -> showLoader()
            is PokemonHabilidadesViewState.Success -> {
                hideLoader()
                displayPokemones(it.response)
            }
            is PokemonHabilidadesViewState.Error -> {
                hideLoader()
                showNotFoundMessage("Error al cargar datos x.x")
            }
        }
    }

    private fun displayPokemones(response: MutableList<Ability>) {
        pokemonesAdapter.updateDataSet(response)
    }

    private fun hideLoader() {
        with(binding) {
            this?.list?.visibility = View.VISIBLE
            this?.pCargar?.visibility = View.GONE
            this?.txtMessageLineEvo?.visibility = View.GONE
        }
    }

    private fun displayError(s: String) {
        with(binding) {
            this?.txtMessageLineEvo?.visibility = View.VISIBLE
            this?.txtMessageLineEvo?.setText(s)
            this?.list?.visibility = View.GONE
            this?.pCargar?.visibility = View.GONE
        }
    }

    private fun showNotFoundMessage(s: String) {
        with(binding) {
            this?.list?.visibility = View.GONE
            this?.pCargar?.visibility = View.GONE
            this?.txtMessageLineEvo?.visibility = View.VISIBLE
            this?.txtMessageLineEvo?.setText(s)
        }
    }
}