package fr.thibma.pokedex.utils

import fr.thibma.pokedex.R
import fr.thibma.pokedex.type.PokemonType

class TypeConverter(val type: PokemonType) {

    fun getImage() : Int {
        val resource = when(type) {
            PokemonType.BUG -> R.drawable.bug_type
            PokemonType.DARK -> R.drawable.dark_type
            PokemonType.DRAGON -> R.drawable.dragon_type
            PokemonType.ELECTRIC -> R.drawable.thunder_type
            PokemonType.FAIRY -> R.drawable.fairy_type
            PokemonType.FIGHTING -> R.drawable.fight_type
            PokemonType.FIRE -> R.drawable.fire_type
            PokemonType.FLYING -> R.drawable.fly_type
            PokemonType.GHOST -> R.drawable.ghost_type
            PokemonType.GRASS -> R.drawable.grass_type
            PokemonType.GROUND -> R.drawable.ground_type
            PokemonType.ICE -> R.drawable.ice_type
            PokemonType.NORMAL -> R.drawable.normal_type
            PokemonType.POISON -> R.drawable.poison_type
            PokemonType.PSYCHIC -> R.drawable.psy_type
            PokemonType.ROCK -> R.drawable.rock_type
            PokemonType.STEEL -> R.drawable.steel_type
            PokemonType.WATER -> R.drawable.water_type
            else -> R.drawable.normal_type
        }
        return resource
    }

}