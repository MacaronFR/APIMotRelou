package fr.imacaron.motrelou.depot

import fr.imacaron.motrelou.type.TMajMot
import fr.imacaron.motrelou.type.TMot
import fr.imacaron.motrelou.type.TNouveauMot

interface DepotMot {
	fun recherche(demande: String, limit: Int, page: Int): List<TMot>
	fun recuperer(mot: String): TMot
	fun recuperer(limit: Int, page: Int): List<TMot>
	fun modifier(mot: TMajMot): TMot
	fun supprimer(mot: String): Boolean
	fun aleatoire(): TMot
	fun ajouter(mot: TNouveauMot): TMot
}