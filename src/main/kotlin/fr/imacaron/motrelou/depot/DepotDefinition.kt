package fr.imacaron.motrelou.depot

import fr.imacaron.motrelou.type.TMajDefinition
import fr.imacaron.motrelou.type.TMot
import fr.imacaron.motrelou.type.TNouvelleDefinition


interface DepotDefinition {

	fun ajouter(mot: String, definition: TNouvelleDefinition): TMot

	fun modifier(mot: String, index: Int, definition: TMajDefinition)

	fun supprimer(mot: String, index: Int)
}