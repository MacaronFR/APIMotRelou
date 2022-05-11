package fr.imacaron.motrelou.bdd

import fr.imacaron.motrelou.depot.DepotMot
import fr.imacaron.motrelou.getTDefinition
import fr.imacaron.motrelou.getTMot
import fr.imacaron.motrelou.type.TMajMot
import fr.imacaron.motrelou.type.TMot
import fr.imacaron.motrelou.type.TNouveauMot
import java.sql.Connection
import java.sql.DriverManager

class BddMot: DepotMot {

	companion object {
		val connection: Connection = DriverManager.getConnection(System.getenv("BDD_URL")!!, System.getenv("BDD_USER")!!, System.getenv("BDD_PASSWORD"))
	}

	override fun recherche(demande: String, limit: Int, page: Int): List<TMot> {
		val stmt = connection.prepareStatement("SELECT mot, MOTS.creation, MOTS.createur, definition, DEFINITIONS.creation, DEFINITIONS.createur, `index` FROM MOTS LEFT JOIN DEFINITIONS ON DEFINITIONS.id_mot = MOTS.id_mot WHERE mot LIKE ? LIMIT ${page*limit}, $limit")
		stmt.setString(1, "'%$demande%'")
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

	override fun recuperer(mot: String): TMot {
		TODO("Not yet implemented")
	}

	override fun recuperer(limit: Int, page: Int): List<TMot> {
		val stmt = connection.createStatement()
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

	override fun modifier(mot: TMajMot): TMot {
		TODO("Not yet implemented")
	}

	override fun supprimer(mot: String): Boolean {
		TODO("Not yet implemented")
	}

	override fun aleatoire(): TMot {
		TODO("Not yet implemented")
	}

	override fun ajouter(mot: TNouveauMot): TMot {
		TODO("Not yet implemented")
	}
}