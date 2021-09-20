package fr.thibma.pokedex.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import fr.thibma.pokedex.R
import fr.thibma.pokedex.activities.fragments.PokedexFragment
import fr.thibma.pokedex.activities.fragments.PokemonDetailFragment

class MainActivity : AppCompatActivity() {

    private lateinit var pokedexFragment: PokedexFragment
    //private lateinit var pokemonDetailFragment: PokemonDetailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokedexFragment = PokedexFragment()
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.mainFragment, pokedexFragment)
        fragment.commit()

    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        }
        else {
            super.onBackPressed()
        }

    }

}
