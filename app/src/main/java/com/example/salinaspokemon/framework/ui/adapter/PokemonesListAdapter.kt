package com.example.salinaspokemon.framework.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.salinaspokemon.R
import com.example.salinaspokemon.data.datasource.db.Pokemon
import com.example.salinaspokemon.databinding.ItemPokemonesListBinding
import com.example.salinaspokemon.framework.data.model.pokemones.Result
import com.example.salinaspokemon.utils.PokemonFavorito

class PokemonesListAdapter(
    private val pokemones: MutableList<Result> = mutableListOf(),
    private val onPokemonesSelected: (Result) -> Unit
) : RecyclerView.Adapter<PokemonesListAdapter.PokemonesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_pokemones_list,
                parent,
                false
            )
        return PokemonesViewHolder(view)
    }

    override fun getItemCount(): Int = pokemones.size

    override fun onBindViewHolder(holder: PokemonesViewHolder, position: Int) =
        holder.bindView(pokemones[position], onPokemonesSelected)

    fun updateDataSet(pokemones: MutableList<Result>) {
        this.pokemones.clear()
        this.pokemones.addAll(pokemones)
        notifyDataSetChanged()

    }

    class PokemonesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPokemonesListBinding.bind(itemView)

        fun bindView(pokemones: Result, onPokemonesSelected: (Result) -> Unit) {
            with(pokemones) {
                if (name.isNotEmpty()) {
                    if (name.equals(PokemonFavorito.pokemon) && !PokemonFavorito.statusRequest.equals("")){
                        binding.txtNombre.text = PokemonFavorito.statusRequest + " - " + PokemonFavorito.pokemon
                        binding.root.setOnClickListener {
                            onPokemonesSelected(this)
                        }
                    } else {
                        binding.txtNombre.text = name
                        binding.root.setOnClickListener {
                            onPokemonesSelected(this)
                        }
                    }

                }
            }
        }
    }
}
