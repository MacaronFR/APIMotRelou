package fr.imacaron.motrelou.depot

import fr.imacaron.motrelou.type.TMajMot
import fr.imacaron.motrelou.type.TMot
import fr.imacaron.motrelou.type.TNouveauMot

interface DepotMot {
	/**
	 * @author MacaronFR
	 * @param demande Mot à rechercher
	 * @param limit Nombre de mots max à chercher
	 * @param page Numéro de la page à récupérer
	 * @return La liste des mots correspondant à la [demande]. S'il n'y a pas de résultat, une liste vide est retournée
	 * Recherche en fonction de la chaine de caractère [demande] et renvoie la page n°[page] en limitant à [limit] résultat.
	 * */
	fun recherche(demande: String, limit: Int, page: Int): List<TMot>

	/**
	 * @author MacaronFR
	 * @param mot Le mot exact à récupérer
	 * @return Le mot demandé
	 * Retourne le [mot] exact ou null si rien n'est trouvé
	 */
	fun recuperer(mot: String): TMot?

	/**
	 * @author MacaronFR
	 * @param limit Le nombre de résultat par page
	 * @param page La page à récupérer
	 * @return Une liste de mot de taille [limit], vide si aucun résultat.
	 * Récupere la [page] avec [limit] résultat
	 */
	fun recuperer(limit: Int, page: Int): List<TMot>

	/**
	 * @author MacaronFR
	 * @param mot Mot à mettre à jour
	 * Met à jour un mot
	 */
	fun modifier(mot: String, maj: TMajMot): TMot

	/**
	 * @author MacaronFR
	 * @param mot Mot exact à supprimer
	 * @return *true* si la supression c'est bien passé, false sinon
	 * Supprime un [mot] exact
	 */
	fun supprimer(mot: String): Boolean

	/**
	 * @author MacaronFR
	 * @return un mot aléatoire
	 * Choisi un mot aléatoire dans la base et le renvoie
	 */
	fun aleatoire(): TMot?

	/**
	 * @author MacaronFR
	 * @param [mot] Nouveau mot à ajouter
	 * @return Le mot ajouté
	 * Ajoute un mot à la base
	 */
	fun ajouter(mot: TNouveauMot): TMot
}