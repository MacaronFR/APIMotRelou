package fr.imacaron.motrelou.domaine

import fr.imacaron.motrelou.depot.DepotDefinition
import fr.imacaron.motrelou.depot.NotFoundException
import fr.imacaron.motrelou.requete.RequetesDefinition
import fr.imacaron.motrelou.type.TMajDefinition
import fr.imacaron.motrelou.type.TMot
import fr.imacaron.motrelou.type.TNouvelleDefinition

class ServiceDefinition(private val depot: DepotDefinition, private val logger: System.Logger): RequetesDefinition {
	override fun creer(definition: TNouvelleDefinition, mot: String): TMot? {
		return try {
			depot.ajouter(mot, definition)
		} catch(e: NotFoundException) {
			logger.log(System.Logger.Level.INFO, "Erreur lors de l'ajout d'une définition: ${e.message}")
			null
		}
	}

	override fun maj(defintion: TMajDefinition, mot: String, index: Int): TMot? {
		return try {
			depot.modifier(mot, index, defintion)
		} catch(e: NotFoundException) {
			logger.log(System.Logger.Level.INFO, "Erreur lors de la modification d'une définition: ${e.message}")
			null
		}
	}

	override fun delete(mot: String, index: Int): Boolean {
		return try {
			depot.supprimer(mot, index)
		}catch(e: NotFoundException) {
			logger.log(System.Logger.Level.INFO, "Erreur lors de la supression d'une définition: ${e.message}")
			false
		}
	}

}