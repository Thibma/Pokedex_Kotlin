package fr.thibma.pokedex.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import fr.thibma.pokedex.PokemonQuery
import fr.thibma.pokedex.PokemonSummaryQuery
import fr.thibma.pokedex.R
import fr.thibma.pokedex.utils.TypeConverter

class PokemonListAdapter(private val pokemonList: List<PokemonSummaryQuery.Pokemon>, private val listener: OnItemClickListener) : RecyclerView.Adapter<PokemonListAdapter.PokemonListViewHolder>() {

    inner class PokemonListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val imageViewPokemon: ImageView = itemView.findViewById(R.id.imageViewPokemon)
        val textViewPokemonName: TextView = itemView.findViewById(R.id.textViewPokemonName)
        val textViewPokedexNumber: TextView = itemView.findViewById(R.id.textViewPokedexNumber)
        val imageViewType1: ImageView = itemView.findViewById(R.id.imageViewType1)
        val imageViewType2: ImageView = itemView.findViewById(R.id.imageViewType2)
        val progressBarWaitingImage: ProgressBar = itemView.findViewById(R.id.progressbarPokemonItem)

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
    ): PokemonListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return PokemonListViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        val currentItem = pokemonList[position]
        if (currentItem.sprite != null) {
            Glide.with(holder.itemView.context)
                .load(currentItem.sprite)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.progressBarWaitingImage.visibility = View.INVISIBLE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.progressBarWaitingImage.visibility = View.INVISIBLE
                        return false
                    }

                })
                .into(holder.imageViewPokemon)
        }
        holder.textViewPokemonName.text = currentItem.name
        holder.textViewPokedexNumber.text = "N°" + currentItem.pokenum.toString()
        Glide.with(holder.itemView.context)
            .load(TypeConverter(currentItem.type!![0]!!).getImage())
            .into(holder.imageViewType1)
        if (currentItem.type.size > 1) {
            holder.imageViewType2.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(TypeConverter(currentItem.type[1]!!).getImage())
                .into(holder.imageViewType2)
        }
    }

    override fun getItemCount(): Int = pokemonList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}