package com.example.salinaspokemon.framework.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.salinaspokemon.databinding.FragmentLineaEvolutivaBinding
import com.example.salinaspokemon.framework.data.model.lineaevolutiva.Species
import com.example.salinaspokemon.framework.presentation.PokemonEvoLinViewModel
import com.example.salinaspokemon.framework.presentation.viewstate.PokemonLineEvoViewState
import com.example.salinaspokemon.framework.ui.adapter.PokemonesLineEvoListAdapter
import com.example.salinaspokemon.utils.ValidarR
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LineaEvolutiva : Fragment() {

    private val pokemonesViewModel: PokemonEvoLinViewModel by viewModels()
    private var _binding: FragmentLineaEvolutivaBinding? = null
    private val binding get() = _binding
    private lateinit var pokemonesAdapter: PokemonesLineEvoListAdapter
    private lateinit var contexto: Context
    lateinit var vista: View
    lateinit var url: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLineaEvolutivaBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contexto = requireContext().applicationContext
        var text = arguments?.getString("lineaevolutiva")?.substring(18)
        url = text.toString()

        attachObservers()
        bindViews(url)
    }

    private fun attachObservers() {
        pokemonesViewModel.pokemonState.observe(viewLifecycleOwner, {
            renderPokemones(it)
        })
    }

    private fun bindViews(url: String): Unit = with(binding) {
        if (ValidarR.hayRed(contexto)) {
            pokemonesViewModel.loadPokemonLine(url)
        } else {
            displayError("No hay internet banda x.x")
        }

        pokemonesAdapter = PokemonesLineEvoListAdapter(onPokemonesSelected = { result ->

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

    private fun renderPokemones(it: PokemonLineEvoViewState) {
        when (it) {
            is PokemonLineEvoViewState.Loading -> showLoader()
            is PokemonLineEvoViewState.Success -> {
                hideLoader()
                displayPokemones(it.response)
            }
            is PokemonLineEvoViewState.Error -> {
                hideLoader()
                showNotFoundMessage("Error al cargar datos x.x")
            }
        }
    }

    private fun displayPokemones(response: MutableList<Species>) {
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