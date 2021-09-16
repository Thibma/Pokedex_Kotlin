package fr.thibma.pokedex.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import fr.thibma.pokedex.PokemonQuery
import fr.thibma.pokedex.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class PokedexActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pokedex_activity)
        MainScope().launch {
            getData()
        }
    }

    private suspend fun getData() {
        coroutineScope {
            val apolloClient = ApolloClient.builder()
                .serverUrl("http://10.144.9.247:4000/graphql")
                .build()
            val response = apolloClient.query(PokemonQuery()).await()
        }
    }
}