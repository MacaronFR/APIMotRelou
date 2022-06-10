package fr.imacaron.motrelou.bdd

import fr.imacaron.motrelou.depot.DepotMot
import fr.imacaron.motrelou.depot.NotFoundException
import fr.imacaron.motrelou.getTDefinition
import fr.imacaron.motrelou.getTMot
import fr.imacaron.motrelou.type.TMajMot
import fr.imacaron.motrelou.type.TMot
import fr.imacaron.motrelou.type.TNouveauMot
import java.sql.Connection
import java.sql.DriverManager
import java.util.Random

/**
 * @author MacaronFR
 * Implémentation de [DepotMot] pour interagir avec une base de donné MySQL
 */
class BddMot: DepotMot {

	companion object {
		/**
		 * @author MacaronFR
		 * Connection à la base de donnée
		 */
		private var connection: Connection = DriverManager.getConnection("jdbc:apache:commons:dbcp:motrelou", System.getenv("BDD_USER")!!,System.getenv("BDD_PASSWORD"))

		fun getConnection(): Connection{
			if(!connection.isValid(10)){
				connection = DriverManager.getConnection("jdbc:apache:commons:dbcp:motrelou", System.getenv("BDD_USER")!!,System.getenv("BDD_PASSWORD"))
			}
			return connection
		}


		/**
		 * @author MacaronFR
		 * Les champs minium à récupérer lors d'une requête
		 */
		const val fields = "mot, MOTS.creation, MOTS.createur, definition, DEFINITIONS.creation, DEFINITIONS.createur, `index`"
	}

	override fun recherche(demande: String, limit: Int, page: Int): List<TMot> {
		val stmt = getConnection().prepareStatement("SELECT $fields FROM MOTS LEFT JOIN DEFINITIONS ON DEFINITIONS.id_mot = MOTS.id_mot WHERE mot LIKE ? LIMIT ${page*limit}, $limit")
		stmt.setString(1, "%$demande%")
		val res = stmt.executeQuery()
		val list = mutableListOf<TMot>()
		while(res.next()){
			list.find { it.mot == res.getString("mot")!! }?.let{ mot ->
				res.getString("definition")?.let{
					mot.definitions.add(res.getTDefinition())
				}
			} ?: run{
				list.add(res.getTMot())
			}
		}
		return list
	}

	override fun recuperer(mot: String): TMot? {
		val stmt = getConnection().prepareStatement("SELECT $fields FROM MOTS LEFT JOIN DEFINITIONS ON DEFINITIONS.id_mot = MOTS.id_mot WHERE mot = ?")
		println(mot)
		stmt.setString(1, mot)
		val res = stmt.executeQuery()
		return if(res.next()){
			val m = res.getTMot()
			while(res.next()){
				m.definitions.add(res.getTDefinition())
			}
			m
		}else{
			null
		}
	}

	override fun recuperer(limit: Int, page: Int): List<TMot> {
		val stmt = getConnection().createStatement()
		val res = stmt.executeQuery("SELECT mot, MOTS.creation, MOTS.createur, definition, DEFINITIONS.creation, DEFINITIONS.createur, `index` FROM MOTS LEFT JOIN DEFINITIONS ON DEFINITIONS.id_mot = MOTS.id_mot LIMIT ${page*limit}, $limit")
		val list = mutableListOf<TMot>()
		while(res.next()){
			list.find { it.mot == res.getString("mot")!! }?.let{ mot ->
				res.getString("definition")?.let{
					mot.definitions.add(res.getTDefinition())
				}
			} ?: run{
				list.add(res.getTMot())
			}
		}
		return list
	}

	override fun modifier(mot: String, maj: TMajMot): TMot {
		val preMot = recuperer(mot) ?: throw NotFoundException("Le mot n'existe pas")
		val stmt = getConnection().prepareStatement("UPDATE MOTS SET mot = ?, createur = ? WHERE mot = ?")
		stmt.setString(1, maj.mot ?: preMot.mot)
		stmt.setString(2, maj.createur ?: preMot.createur)
		stmt.setString(3, mot)
		stmt.executeUpdate()
		return recuperer(maj.mot ?: mot)!!
	}

	override fun supprimer(mot: String): Boolean {
		val stmt = getConnection().prepareStatement("DELETE FROM MOTS WHERE mot = ?")
		stmt.setString(1, mot)
		return stmt.executeUpdate() != 0
	}

	/**
	 * @author MacaronFR
	 * @return Total de mots
	 * Retourne le nombre de mots en base
	 */
	private fun compte(): Int{
		return getConnection().createStatement().executeQuery("SELECT COUNT(id_mot) as tot FROM MOTS").let {
			it.next()
			it.getInt("tot")
		}
	}

	override fun aleatoire(): TMot? {
		var res = getConnection().createStatement().executeQuery("SELECT id_mot FROM MOTS LIMIT ${Random().nextInt(0, compte())}, 1")
		return if(res.next()){
			val id = res.getInt("id_mot")
			res = getConnection().createStatement().executeQuery("SELECT $fields FROM MOTS LEFT JOIN DEFINITIONS ON MOTS.id_mot = DEFINITIONS.id_mot WHERE MOTS.id_mot = $id")
			if(res.next()) {
				val m = res.getTMot()
				while(res.next())
					m.definitions.add(res.getTDefinition())
				m
			}else{
				null
			}
		}else{
			null
		}
	}

	override fun ajouter(mot: TNouveauMot): TMot {
		var stmt = getConnection().prepareStatement("INSERT INTO MOTS (mot, createur) VALUES (?, ?)")
		stmt.setString(1, mot.mot)
		stmt.setString(2, mot.createur)
		stmt.executeUpdate()
		val idMot = recupererId(mot.mot)
		stmt = getConnection().prepareStatement("INSERT INTO DEFINITIONS (definition, createur, id_mot, `index`) VALUES (?, ?, ?, 1)")
		stmt.setString(1, mot.definition)
		stmt.setString(2, mot.createur)
		stmt.setInt(3, idMot)
		stmt.execute()
		stmt = getConnection().prepareStatement("SELECT $fields, MOTS.id_mot FROM MOTS LEFT JOIN DEFINITIONS ON MOTS.id_mot = DEFINITIONS.id_mot WHERE mot = ?")
		stmt.setString(1, mot.mot)
		return stmt.executeQuery().let{
			it.next()
			it.getTMot()
		}
	}

	/**
	 * @author MacaronFR
	 * @param mot Le mot dont on veut l'id
	 * @return L'id du [mot]
	 * Permet de récupérer l'id en base du [mot]
	 */
	fun recupererId(mot: String): Int{
		val stmt = getConnection().prepareStatement("SELECT id_mot FROM MOTS WHERE mot = ?")
		stmt.setString(1, mot)
		return stmt.executeQuery().let{
			it.next()
			it.getInt("id_mot")
		}
	}
}