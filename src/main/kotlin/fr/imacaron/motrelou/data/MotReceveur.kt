package fr.imacaron.motrelou.data

import fr.imacaron.motrelou.data.types.TMajMot
import fr.imacaron.motrelou.data.types.TMot

interface MotReceveur {
	fun recherche(demande: String): List<TMot>

	fun recuperer(mot: String): TMot

	fun modifier(mot: TMajMot): TMot

	fun supprimer(mot: String): Boolean

	fun aleatoire(): TMot
}