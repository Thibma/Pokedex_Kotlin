package fr.thibma.pokedex.activities.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import fr.thibma.pokedex.PokemonQuery
import fr.thibma.pokedex.PokemonSummaryQuery
import fr.thibma.pokedex.R
import fr.thibma.pokedex.adapter.PokemonListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class PokedexFragment : Fragment(), CoroutineScope by MainScope(), PokemonListAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private var pokemonList: List<PokemonSummaryQuery.Pokemon> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokedex, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = requireActivity().findViewById(R.id.recyclerViewPokemonList)

        MainScope().launch {
            getData()
        }
    }

    private suspend fun getData() {
        coroutineScope {
            try {
                val apolloClient = ApolloClient.builder()
                    .serverUrl("http://mocprojects.spell.ovh:4000/graphql")
                    .build()
                val response = apolloClient.query(PokemonSummaryQuery()).await()

                pokemonList = response.data?.pokemons!!
                pokemonList = pokemonList.sortedBy {
                    it.pokenum?.toInt()
                }

                val pokemonListAdapter = PokemonListAdapter(pokemonList, this@PokedexFragment)
                pokemonListAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                recyclerView.adapter = pokemonListAdapter
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.setHasFixedSize(true)
            }
            catch (e: Exception) {
                Log.d("CRASH","Crash serveur")
            }

        }
    }

    override fun onItemClick(position: Int) {
        MainScope().launch {
            getPokemon(pokemonList[position].pokenum!!)
        }
    }

    private suspend fun getPokemon(pokemon: String) {
        coroutineScope {
            val apolloClient = ApolloClient.builder()
                .serverUrl("http://mocprojects.spell.ovh:4000/graphql")
                .build()
            val response = apolloClient.query(PokemonQuery(Input.fromNullable(pokemon))).await()

            val fragment = requireActivity().supportFragmentManager.beginTransaction()
            fragment.replace(R.id.mainFragment, PokemonDetailFragment(response.data?.pokemon!!)).addToBackStack(null)
            fragment.commit()
        }
    }
}