package fr.imacaron.motrelou.requete

import fr.imacaron.motrelou.type.TMajDefinition
import fr.imacaron.motrelou.type.TMot
import fr.imacaron.motrelou.type.TNouvelleDefinition

/**
 * @author MacaronFR
 * Interface avec la logique métier de l'API
 */
interface RequetesDefinition {

	/**
	 * @author MacaronFR
	 * @param definition La définition à créer
	 * @param mot Le mot auquel ajouter ma définition
	 * @return Un [TMot] si tout a fonctionner, null sinon
	 * Permet de créer une définition pour le [mot]
	 */
	fun creer(definition: TNouvelleDefinition, mot: String): TMot?

	/**
	 *
	 */
	fun maj(defintion: TMajDefinition, mot: String, index: Int): TMot?
	fun delete(mot: String, index: Int): Boolean
}