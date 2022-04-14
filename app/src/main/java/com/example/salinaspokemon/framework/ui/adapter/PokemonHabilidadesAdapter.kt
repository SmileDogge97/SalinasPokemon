package com.example.salinaspokemon.framework.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.salinaspokemon.R
import com.example.salinaspokemon.databinding.ItemPokemonesListBinding
import com.example.salinaspokemon.framework.data.model.habilidades.Ability

class PokemonHabilidadesAdapter(
    private val pokemones: MutableList<Ability> = mutableListOf(),
    private val onPokemonesSelected: (Ability) -> Unit
) : RecyclerView.Adapter<PokemonHabilidadesAdapter.PokemonesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_pokemones_list,
            parent,
            false
        )
        return PokemonesViewHolder(view)
    }

    override fun getItemCount(): Int = pokemones.size

    override fun onBindViewHolder(holder: PokemonesViewHolder,position: Int) =
        holder.bindView(pokemones[position], onPokemonesSelected)

    fun updateDataSet(response: MutableList<Ability>) {
        this.pokemones.clear()
        this.pokemones.addAll(response)
        notifyDataSetChanged()
    }

    class PokemonesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPokemonesListBinding.bind(itemView)

        fun bindView(pokemones: Ability, onPokemonesSelected: (Ability) -> Unit) {
            with(pokemones){
                if (ability.name.isNotEmpty()){
                    binding.txtNombre.text = ability.name
                    binding.root.setOnClickListener {
                        onPokemonesSelected(this)
                    }
                }
            }
        }
    }
}
