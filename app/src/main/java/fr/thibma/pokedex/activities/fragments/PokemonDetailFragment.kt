package fr.thibma.pokedex.activities.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.bumptech.glide.Glide
import fr.thibma.pokedex.PokemonQuery
import fr.thibma.pokedex.R
import fr.thibma.pokedex.adapter.FamilyListAdapter
import fr.thibma.pokedex.adapter.MoveListAdapter
import fr.thibma.pokedex.adapter.TalentListAdapter
import fr.thibma.pokedex.utils.TypeConverter
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

class PokemonDetailFragment(private var pokemon: PokemonQuery.Pokemon) : Fragment(), FamilyListAdapter.OnItemClickListener{

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
    private lateinit var moveRecyclerView: RecyclerView
    private lateinit var familyRecyclerView: RecyclerView

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
        moveRecyclerView = requireActivity().findViewById(R.id.recyclerViewMove)
        familyRecyclerView = requireActivity().findViewById(R.id.recyclerViewFamily)

        leftPokemonButton.setOnClickListener {
            MainScope().launch {
                getPokemonNumber(pokemon.pokenum!!.toInt() - 1)
            }
        }

        rightPokemonButton.setOnClickListener {
            MainScope().launch {
                getPokemonNumber(pokemon.pokenum!!.toInt() + 1)
            }
        }

        loadElements()

    }

    private fun loadElements() {
        Glide.with(this)
            .load(pokemon.sprite)
            .into(imageViewPokemon)

        Glide.with(this)
            .load(pokemon.spriteSmall)
            .into(miniPokemonImageView)

        pokedexNumberTextView.text = "N°" + pokemon.pokenum.toString()
        pokemonNameTextView.text = pokemon.name

        typeImageView1.setImageResource(TypeConverter(pokemon.type!![0]!!).getImage())
        if (pokemon.type!!.size < 2) {
            typeImageView2.visibility = View.INVISIBLE
        }
        else {
            typeImageView2.visibility = View.VISIBLE
            typeImageView2.setImageResource(TypeConverter(pokemon.type!![1]!!).getImage())
        }
        heightTextView.text = "Taille : " + pokemon.height.toString() + "cm"
        weightTextView.text = "Poids : " + pokemon.weight.toString() + "kg"

        pokemonCategoryTextView.text = pokemon.species
        pokedexDescriptionTextView.text = pokemon.description

        val pokemonTalentListAdapter = TalentListAdapter(pokemon.talents)
        talentRecyclerView.isNestedScrollingEnabled = false
        talentRecyclerView.adapter = pokemonTalentListAdapter
        talentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        talentRecyclerView.setHasFixedSize(true)

        val pokemonMoveListAdapter = MoveListAdapter(pokemon.moves)
        moveRecyclerView.isNestedScrollingEnabled = false
        moveRecyclerView.adapter = pokemonMoveListAdapter
        moveRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        moveRecyclerView.setHasFixedSize(true)

        val familyListAdapter = FamilyListAdapter(pokemon.evolutions, this)
        familyRecyclerView.isNestedScrollingEnabled = false
        familyRecyclerView.adapter = familyListAdapter
        familyRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        familyRecyclerView.setHasFixedSize(true)
    }

    private suspend fun getPokemonNumber(pokenum: Int) {
        if (pokenum == 0) {
            return
        }

        coroutineScope {
            try {
                val apolloClient = ApolloClient.builder()
                    .serverUrl("http://mocprojects.spell.ovh:4000/graphql")
                    .build()
                val response = apolloClient.query(PokemonQuery(Input.fromNullable(pokenum.toString()))).await()

                pokemon = response.data?.pokemon!!
                loadElements()
            }
            catch (e: Exception) {
                return@coroutineScope
            }
        }

    }

    override fun onItemClick(position: Int) {
        MainScope().launch {
            getPokemonNumber(pokemon.evolutions?.get(position)?.pokenum!!.toInt())
        }
    }
}