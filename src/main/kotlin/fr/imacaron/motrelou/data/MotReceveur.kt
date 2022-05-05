package fr.imacaron.motrelou.data

import fr.imacaron.motrelou.data.types.TMot

interface MotReceveur {
	fun recherche(demande: String): List<TMot>

	fun recuperer(mot: String): TMot
}