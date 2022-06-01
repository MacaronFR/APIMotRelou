package fr.imacaron.motrelou.depot

import fr.imacaron.motrelou.type.TMajDefinition
import fr.imacaron.motrelou.type.TMot
import fr.imacaron.motrelou.type.TNouvelleDefinition

/**
 * @author MacaronFR
 * Interface d'interaction avec la donnée définition
 */
interface DepotDefinition {

	/**
	 * @author MacaronFR
	 * @param mot Le mot auquel ajouter une définition
	 * @param definition La definition à ajouter
	 * @return Le [TMot] avec la nouvelle définition
	 * Permet d'ajouter une définition à un mot
	 */
	fun ajouter(mot: String, definition: TNouvelleDefinition): TMot

	/**
	 * @author MacaronFR
	 * @param mot Le mot duquel modifier la définition
	 * @param index L'index de la définititon à modifier
	 * @param definition La modification à apporter
	 * @return Le [TMot] avec la définition modifier
	 * Permet de modifier une définition d'un mot
	 */
	fun modifier(mot: String, index: Int, definition: TMajDefinition): TMot

	/**
	 * @author MacaronFR
	 * @param mot Le mot duquel supprimer la définition
	 * @param index L'index de la définition à supprimer
	 * @return Un [Boolean] vrai si la suppression a bien fonctionné, faux sinon
	 * Permet de supprimer la définition d'un mot
	 */
	fun supprimer(mot: String, index: Int): Boolean
}