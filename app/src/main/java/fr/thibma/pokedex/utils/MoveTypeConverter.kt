package fr.thibma.pokedex.utils

import fr.thibma.pokedex.R
import fr.thibma.pokedex.type.MoveType

class MoveTypeConverter(private val moveType: MoveType) {

    fun getImage(): Int {
        val resource = when(moveType) {
            MoveType.PHYSICAL -> R.drawable.physical
            MoveType.SPECIAL -> R.drawable.special
            MoveType.STATUS -> R.drawable.status
            else -> R.drawable.status
        }
        return resource
    }
}