package fr.imacaron.motrelou.domaine

import fr.imacaron.motrelou.depot.DepotDefinition
import fr.imacaron.motrelou.depot.NotFoundException
import fr.imacaron.motrelou.requete.RequetesDefinition
import fr.imacaron.motrelou.type.TMajDefinition
import fr.imacaron.motrelou.type.TMot
import fr.imacaron.motrelou.type.TNouvelleDefinition
import org.slf4j.Logger

class ServiceDefinition(private val depot: DepotDefinition, private val logger: Logger): RequetesDefinition {
	override fun creer(definition: TNouvelleDefinition, mot: String): TMot? {
		return try {
			depot.ajouter(mot, definition)
		} catch(e: NotFoundException) {
			logger.info("Erreur lors de l'ajout d'une définition: ${e.message}")
			null
		}
	}

	override fun maj(defintion: TMajDefinition, mot: String, index: Int): TMot? {
		return try {
			depot.modifier(mot, index, defintion)
		} catch(e: NotFoundException) {
			logger.info("Erreur lors de la modification d'une définition: ${e.message}")
			null
		}
	}

	override fun delete(mot: String, index: Int): Boolean {
		return try {
			depot.supprimer(mot, index)
		}catch(e: NotFoundException) {
			logger.info("Erreur lors de la supression d'une définition: ${e.message}")
			false
		}
	}

}