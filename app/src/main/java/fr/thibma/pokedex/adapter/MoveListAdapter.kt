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
import fr.thibma.pokedex.utils.MoveTypeConverter
import fr.thibma.pokedex.utils.TypeConverter

class MoveListAdapter(private val moveList: List<PokemonQuery.Move?>?) : RecyclerView.Adapter<MoveListAdapter.MoveListViewHolder>() {

    inner class MoveListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moveNameTextView: TextView = itemView.findViewById(R.id.textViewMoveName)
        val typeImageView: ImageView = itemView.findViewById(R.id.imageViewMoveType)
        val moveTypeImageView: ImageView = itemView.findViewById(R.id.imageViewMoveCategory)
        val powerTextView: TextView = itemView.findViewById(R.id.textViewPower)
        val precisionTextView: TextView = itemView.findViewById(R.id.textViewPrecision)
        val ppTextView: TextView = itemView.findViewById(R.id.textViewPP)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoveListAdapter.MoveListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_move, parent, false)
        return MoveListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MoveListAdapter.MoveListViewHolder, position: Int) {
        val currentItem = moveList?.get(position)

        if (currentItem != null) {
            holder.moveNameTextView.text = currentItem.name
            Glide.with(holder.itemView.context)
                .load(MoveTypeConverter(currentItem.moveType!!).getImage())
                .into(holder.moveTypeImageView)

            Glide.with(holder.itemView.context)
                .load(TypeConverter(currentItem.type!!).getImage())
                .into(holder.typeImageView)

            if (currentItem.power == null) {
                holder.powerTextView.text = "-"
            }
            else {
                holder.powerTextView.text = currentItem.power.toString()
            }

            if (currentItem.precision == null) {
                holder.precisionTextView.text = "-"
            }
            else {
                holder.precisionTextView.text = currentItem.precision.toInt().toString()
            }

            holder.ppTextView.text = currentItem.powerPoint.toString()
        }

    }

    override fun getItemCount(): Int = moveList!!.size

}