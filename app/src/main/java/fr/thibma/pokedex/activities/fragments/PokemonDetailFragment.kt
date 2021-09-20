package fr.thibma.pokedex.activities.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.thibma.pokedex.PokemonQuery
import fr.thibma.pokedex.R
import fr.thibma.pokedex.adapter.TalentListAdapter
import fr.thibma.pokedex.utils.TypeConverter

class PokemonDetailFragment(private val pokemon: PokemonQuery.Pokemon) : Fragment() {

    private lateinit var miniPokemonImageView: ImageView
    private lateinit var leftPokemonButton: ImageButton
    private lateinit var rightPokemonButton: ImageButton
    private lateinit var imageViewPokemon: ImageView
    private lateinit var pokedexNumberTextView: TextView
    private lateinit var pokemonNameTextView: TextView
    private lateinit var pokemonCategoryTextView: TextView
    private lateinit var typeImageView1: ImageView
    private lateinit var typeImageView2: ImageView
    private lateinit var pokedexDescriptionTextView: TextView
    private lateinit var heightTextView: TextView
    private lateinit var weightTextView: TextView
    private lateinit var talentRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_detail, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        miniPokemonImageView = requireActivity().findViewById(R.id.imageViewPokemonMini)
        leftPokemonButton = requireActivity().findViewById(R.id.imageButtonPokemonLeft)
        rightPokemonButton = requireActivity().findViewById(R.id.imageButtonPokemonRight)
        imageViewPokemon = requireActivity().findViewById(R.id.imageViewPokemonDetail)
        pokedexNumberTextView = requireActivity().findViewById(R.id.textViewPokedexNumberDetail)
        pokemonNameTextView = requireActivity().findViewById(R.id.textViewPokemonNameDetail)
        pokemonCategoryTextView = requireActivity().findViewById(R.id.textViewPokemonCategory)
        typeImageView1 = requireActivity().findViewById(R.id.imageViewType1Detail)
        typeImageView2 = requireActivity().findViewById(R.id.imageViewType2Detail)
        pokedexDescriptionTextView = requireActivity().findViewById(R.id.textViewPokedexDescription)
        heightTextView = requireActivity().findViewById(R.id.textViewHeight)
        weightTextView = requireActivity().findViewById(R.id.textViewWeight)
        talentRecyclerView = requireActivity().findViewById(R.id.recyclerViewTalent)

        Glide.with(this)
            .load(pokemon.sprite)
            .into(imageViewPokemon)

        pokedexNumberTextView.text = "N°" + pokemon.pokenum.toString()
        pokemonNameTextView.text = pokemon.name

        typeImageView1.setImageResource(TypeConverter(pokemon.type!![0]!!).getImage())
        if (pokemon.type.size < 2) {
            typeImageView2.visibility = View.INVISIBLE
        }
        else {
            typeImageView2.setImageResource(TypeConverter(pokemon.type[1]!!).getImage())
        }
        heightTextView.text = "Taille : " + pokemon.height.toString() + "cm"
        weightTextView.text = "Poids : " + pokemon.weight.toString() + "kg"

        leftPokemonButton.setOnClickListener {
            Log.d("GAUCHE", "Pokemon de gauche")
        }

        rightPokemonButton.setOnClickListener {
            Log.d("DROIT", "Pokémon de droite")
        }

        pokemonCategoryTextView.text = pokemon.species
        pokedexDescriptionTextView.text = pokemon.description

        val pokemonTalentListAdapter = TalentListAdapter(pokemon.talents)
        talentRecyclerView.adapter = pokemonTalentListAdapter
        talentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        talentRecyclerView.setHasFixedSize(true)
    }
}