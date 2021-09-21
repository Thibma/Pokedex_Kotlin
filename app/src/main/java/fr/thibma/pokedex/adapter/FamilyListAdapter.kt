package fr.thibma.pokedex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.thibma.pokedex.PokemonQuery
import fr.thibma.pokedex.R

class FamilyListAdapter(private val listPokemon: List<PokemonQuery.Evolution?>?, private val listener: OnItemClickListener) : RecyclerView.Adapter<FamilyListAdapter.FamilyListViewHolder>() {

    inner class FamilyListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val pokemonImageView: ImageView = itemView.findViewById(R.id.imageViewFamilyPokemon)
        val pokemonNameTextView: TextView = itemView.findViewById(R.id.textViewFamilyPokemon)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FamilyListAdapter.FamilyListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_family, parent, false)
        return FamilyListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FamilyListAdapter.FamilyListViewHolder, position: Int) {
        val currentItem = listPokemon?.get(position)

        if (currentItem != null) {
            Glide.with(holder.itemView.context)
                .load(currentItem.sprite)
                .into(holder.pokemonImageView)

            holder.pokemonNameTextView.text = currentItem.name
        }


    }

    override fun getItemCount(): Int = listPokemon!!.size
}