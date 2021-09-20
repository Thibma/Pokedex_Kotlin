package fr.thibma.pokedex.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.thibma.pokedex.PokemonQuery
import fr.thibma.pokedex.R

class TalentListAdapter(private val talentList: List<PokemonQuery.Talent?>?) : RecyclerView.Adapter<TalentListAdapter.TalentListViewHolder>() {

    inner class TalentListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTalentTextView: TextView = itemView.findViewById(R.id.textViewTalentName)
        val descriptionTalentTextView: TextView = itemView.findViewById(R.id.textViewTalentDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalentListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_talent, parent, false)
        return TalentListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TalentListViewHolder, position: Int) {
        val currentItem = talentList?.get(position)

        if (currentItem != null) {
            holder.titleTalentTextView.text = currentItem.name
            holder.descriptionTalentTextView.text = currentItem.description
        }

    }

    override fun getItemCount(): Int = talentList!!.size
}