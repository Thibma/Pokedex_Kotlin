package fr.thibma.pokedex.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.thibma.pokedex.R

class PokemonDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_detail)
    }
}