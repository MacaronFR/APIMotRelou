package fr.imacaron.motrelou.data

import fr.imacaron.motrelou.data.types.TMajDefinition
import fr.imacaron.motrelou.data.types.TMot
import fr.imacaron.motrelou.data.types.TNouvelleDefinition


interface DefinitionReceveur {

	fun ajouter(mot: String, definition: TNouvelleDefinition): TMot

	fun modifier(mot: String, index: Int, definition: TMajDefinition)

	fun supprimer(mot: String, index: Int)
}