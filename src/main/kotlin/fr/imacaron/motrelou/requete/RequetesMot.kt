package fr.imacaron.motrelou.requete

import fr.imacaron.motrelou.type.TMajMot
import fr.imacaron.motrelou.type.TMot
import fr.imacaron.motrelou.type.TNouveauMot

interface RequetesMot {
	fun recherche(recherche: String?, page: Int = 0): List<TMot>
	fun creer(mot: TNouveauMot): TMot?
	fun recuperer(mot: String): TMot?
	fun maj(mot: String, maj: TMajMot): TMot?
	fun supprimer(mot: String): Boolean
	fun aleatoire(): TMot?
}