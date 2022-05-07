package fr.imacaron.motrelou.requete

import fr.imacaron.motrelou.type.TMajDefinition
import fr.imacaron.motrelou.type.TMot
import fr.imacaron.motrelou.type.TNouvelleDefinition

interface RequetesDefinition {
	fun creer(definition: TNouvelleDefinition, mot: String): TMot?
	fun maj(defintion: TMajDefinition, mot: String, index: Int): TMot?
	fun delete(mot: String, index: Int): Boolean
}