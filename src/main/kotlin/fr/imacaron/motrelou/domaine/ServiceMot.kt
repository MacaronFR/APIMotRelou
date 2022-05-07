package fr.imacaron.motrelou.domaine

import fr.imacaron.motrelou.depot.ConflictException
import fr.imacaron.motrelou.depot.DepotMot
import fr.imacaron.motrelou.depot.NotFoundException
import fr.imacaron.motrelou.requete.RequetesMot
import fr.imacaron.motrelou.type.TMajMot
import fr.imacaron.motrelou.type.TMot
import fr.imacaron.motrelou.type.TNouveauMot

class ServiceMot(private val depot: DepotMot, private val logger: System.Logger, private val limit: Int = 20): RequetesMot {
	override fun recherche(recherche: String?, page: Int): List<TMot> {
		return if(recherche != null){
			depot.recherche(recherche, limit, page)
		}else{
			depot.recuperer(limit, page)
		}
	}

	override fun creer(mot: TNouveauMot): TMot? {
		return try{
			depot.ajouter(mot)
		}catch(e: ConflictException){
			logger.log(System.Logger.Level.INFO, "Erreur lors de la creation d'un mot: ${e.message}")
			null
		}
	}

	override fun recuperer(mot: String): TMot? {
		return try{
			depot.recuperer(mot)
		}catch(e: NotFoundException){
			logger.log(System.Logger.Level.INFO, "Erreur lors de la récupération d'un mot: ${e.message}")
			null
		}
	}

	override fun maj(mot: TMajMot): TMot? {
		return try {
			depot.modifier(mot)
		}catch(e: NotFoundException){
			logger.log(System.Logger.Level.INFO, "Erreur lors de la modification d'un mot: ${e.message}")
			null
		}
	}

	override fun supprimer(mot: String): Boolean {
		return try{
			depot.supprimer(mot)
		}catch(e: NotFoundException){
			logger.log(System.Logger.Level.INFO, "Erreur lors de la supression d'un mot: ${e.message}")
			false
		}
	}

	override fun aleatoire(): TMot? {
		return try{
			depot.aleatoire()
		}catch(e: NotFoundException){
			logger.log(System.Logger.Level.INFO, "Erreur lors de la récupération d'un mot: ${e.message}")
			null
		}
	}
}